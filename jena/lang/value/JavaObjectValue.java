package jena.lang.value;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import jena.lang.text.TextWriter;

public final class JavaObjectValue implements Value
{
    Object obj;
    static Map<String, Function<Object, Value>> objectToValueMap;
    List<Method> methods;

    public JavaObjectValue(Object obj)
    {
        this.obj = obj;
        if(obj != null) methods = List.of(obj.getClass().getMethods());
    }

    static
    {
        objectToValueMap = Map.ofEntries(
            Map.entry("char", v -> new CharacterValue((Character)v)),
            Map.entry("byte", v -> new NumberValue((Byte)v)),
            Map.entry("int", v -> new NumberValue((Integer)v)),
            Map.entry("long", v -> new NumberValue((Long)v)),
            Map.entry("float", v -> new NumberValue((Float)v)),
            Map.entry("double", v -> new NumberValue((Double)v)),

            Map.entry("Character", v -> new CharacterValue((Character)v)),
            Map.entry("Byte", v -> new NumberValue((Byte)v)),
            Map.entry("Integer", v -> new NumberValue((Integer)v)),
            Map.entry("Long", v -> new NumberValue((Long)v)),
            Map.entry("Float", v -> new NumberValue((Float)v)),
            Map.entry("Double", v -> new NumberValue((Double)v))
        );
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write(obj.toString());
    }

    @Override
    public Value call(Value argument)
    {
        if(obj == null) return NoneValue.instance;
        if(argument instanceof SymbolValue s)
        {
            var filtered = methods.stream().filter(m -> s.name.compareString(m.getName())).toList();
            return FunctionValue.parameterizedFunction(
                s.name.string(),
                filtered,
                obj.getClass(),
                m -> m.getReturnType(),
                m -> m.getParameterTypes(),
                m -> args -> m.invoke(obj, args));
        }
        throw new RuntimeException(String.format("Argument %s can't be used : it must be a symbol", argument.string()));
    }

    @Override
    public boolean valueEquals(Value v)
    {
        return v instanceof JavaObjectValue j && j.obj.equals(obj);
    }

    public interface Executable
    {
        Object call(Object[] args) throws Throwable;
    }

    public static Supplier<String> typeParametersText(Class<?>[] types)
    {
        return () ->
        {
            StringBuilder typesString = new StringBuilder();
            for(int i = 0; i < types.length; i++)
            {
                if(i != 0) typesString.append(", ");
                typesString.append(types[i].getName());
            }
            return typesString.toString();
        };
    }

    public static Value fromObject(Object obj)
    {
        if(obj == null) return NoneValue.instance;
        if(obj.getClass().isPrimitive())
        {
            var func = objectToValueMap.get(obj.getClass().getName());
            if(func != null) return func.apply(obj);
        }
        if(obj instanceof String s) return new TextValue(s);
        
        return new JavaObjectValue(obj);
    }

    @Override
    public Object toObject(Class<?> type)
    {
        var t = obj.getClass();
        if(type.isAssignableFrom(t)) return obj;
        else throw new RuntimeException(String.format("Type %s is not assignable from %s", type.getName(), t.getName()));
    }
}