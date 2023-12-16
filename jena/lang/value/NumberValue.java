package jena.lang.value;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.function.Function;

import jena.lang.GenericBuffer;
import jena.lang.text.StringText;
import jena.lang.text.TextWriter;

public final class NumberValue implements Value, Single
{
    private double value;
    private static MembersMap<NumberValue> members;
    
    private static Map<String, Function<Number, Object>> objectToValueMap;
    static
    {
        objectToValueMap = Map.ofEntries(
            Map.entry("char", v -> (char)v.intValue()),
            Map.entry("boolean", v -> v.intValue() == 0 ? false : true),
            Map.entry("byte", v -> v.byteValue()),
            Map.entry("int", v -> v.intValue()),
            Map.entry("long", v -> v.longValue()),
            Map.entry("float", v -> v.floatValue()),
            Map.entry("double", v -> v.doubleValue()),

            Map.entry("java.lang.Character", v -> (char)v.intValue()),
            Map.entry("java.lang.Boolean", v -> v.intValue() == 0 ? false : true),
            Map.entry("java.lang.Byte", v -> v.byteValue()),
            Map.entry("java.lang.Integer", v -> v.intValue()),
            Map.entry("java.lang.Long", v -> v.longValue()),
            Map.entry("java.lang.Float", v -> v.floatValue()),
            Map.entry("java.lang.Double", v -> v.doubleValue()),
            Map.entry("java.lang.Object", v -> v)
        );
        members = new MembersMap<NumberValue>(action ->
        {
            action.call("add", self -> self);
            action.call("sub", self -> new NumberValue(-self.single()));
            action.call("mul", numberMethod("mulBy", (a, b) -> a * b));
            action.call("div", numberMethod("divBy", (a, b) -> a * b));
            action.call("mod", numberMethod("modBy", (a, b) -> a % b));
            action.call("greater", numberMethod("greaterThan", (a, b) -> a > b ? 1 : 0));
            action.call("less", numberMethod("lessThan", (a, b) -> a < b ? 1 : 0));
            action.call("equals", numberMethod("equalTo", (a, b) -> a == b ? 1 : 0));
            action.call("notEquals", numberMethod("notEqualTo", (a, b) -> a != b ? 1 : 0));
            action.call("and", numberMethod("andWith", (a, b) -> (long)a & (long)b));
            action.call("or", numberMethod("orWith", (a, b) -> (long)a | (long)b));
            action.call("sqrt", numberMethod(a -> Math.sqrt(a)));
            action.call("negative", numberMethod(a -> -a));
            action.call("not", numberMethod(a -> a == 0 ? 1 : 0));
            action.call("times", self -> new ArrayValue(GenericBuffer.range(self.integer()).map(NumberValue::new)));
        });
    }

    public NumberValue(boolean value)
    {
        this(value ? 1 : 0);
    }

    public NumberValue(double value)
    {
        this.value = value;
    }

    static MembersMap.Member<NumberValue> numberMethod(NumberUnaryFunction function)
    {
        return self -> new NumberValue(function.call(self.single()));
    }

    static MembersMap.Member<NumberValue> numberMethod(String argumentName, NumberBinaryFunction function)
    {
        return self -> new FunctionValue(argumentName, arg ->
        {
            return new NumberValue(function.call(self.single(), Single.of(arg).single()));
        });
    }

    static DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    public void print(TextWriter writer)
    {
        if(value == (int)value)
        {
            writer.write(String.valueOf((int)value));
        }
        else
        {
            writer.write(new StringText(decimalFormat.format(value)));
        }
    }
    @Override
    public Value call(Value argument)
    {
        if(argument instanceof SymbolValue s) return members.member(s.name.string(), self -> self).call(this);
        return new NumberValue(value + Single.of(argument).single());
    }

    @Override
    public double single()
    {
        return value;
    }

    @Override
    public boolean valueEquals(Value v)
    {
        return v instanceof Single s && s.single() == value;
    }

    @Override
    public Object toObject(Class<?> type)
    {
        var func = objectToValueMap.get(type.getName());
        if(func != null) return func.apply(value);
        else throw new RuntimeException(String.format("Type %s expected to be primitive", type.getName()));
    }

    @Override
    public int valueCode()
    {
        return Double.hashCode(value);
    }
}