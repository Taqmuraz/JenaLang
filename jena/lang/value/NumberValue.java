package jena.lang.value;

import java.util.Map;
import java.util.function.Function;

import jena.lang.ArrayBuffer;
import jena.lang.GenericBuffer;
import jena.lang.GenericPair;
import jena.lang.StructPair;
import jena.lang.text.StringText;
import jena.lang.text.TextWriter;

public final class NumberValue implements Value, Single
{
    private double value;
    private Value members;
    
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
    }

    public NumberValue(double value)
    {
        this.value = value;
        this.members = new SymbolMapValue(symbolValueAction ->
            new ArrayBuffer<String>(new String[]
            {
                "mul",    
                "div",
                "greater",
                "less",
                "equals",
                "notEquals",
                "and",
                "or",
            })
            .flow()
            .zip(new ArrayBuffer<NumberFunction>(new NumberFunction[]
            {
                arg -> value * arg,
                arg -> value / arg,
                arg -> value > arg ? 1 : 0,
                arg -> value < arg ? 1 : 0,
                arg -> value == arg ? 1 : 0,
                arg -> value != arg ? 1 : 0,
                arg -> (long)value & (long)arg,
                arg -> (long)value | (long)arg,
            })
            .flow()).<GenericPair<String, ValueFunction>>map(p -> action -> p.both((n, f) -> action.call(n, () -> numberMethod(n, f))))
            .append(new StructPair<String, ValueFunction>("sqrt", () -> new NumberValue((int)Math.sqrt(value))))
            .append(new StructPair<String, ValueFunction>("negative", () -> new NumberValue(-value)))
            .append(new StructPair<String, ValueFunction>("not", () -> new NumberValue(value == 0 ? 1 : 0)))
            .append(new StructPair<String, ValueFunction>("times", () -> new ArrayValue(new GenericBuffer<Value>()
            {
                @Override
                public int length()
                {
                    return (int)value;
                }
                @Override
                public Value at(int index)
                {
                    return new NumberValue(index);
                }
            })
        ))
        .append(new StructPair<>("add", () -> this))
        .append(new StructPair<>("sub", () -> new NumberValue(-value)))
        .read(p -> p.both(symbolValueAction::call)), arg -> new NumberValue(new ExpressionNumber(arg).single() + value));
    }

    static Value numberMethod(String argumentName, NumberFunction function)
    {
        return new FunctionValue("number", arg ->
        {
            if(arg instanceof Single) return new NumberValue(function.call(((Single)arg).single()));
            else return NoneValue.instance;
        });
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write(new StringText(String.valueOf(value)));
    }
    @Override
    public Value call(Value argument)
    {
        return members.call(argument);
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