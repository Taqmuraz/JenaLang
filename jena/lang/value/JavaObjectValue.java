package jena.lang.value;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jena.lang.ArrayBuffer;
import jena.lang.text.TextWriter;

public final class JavaObjectValue implements Value
{
    Object obj;
    static Map<String, Function<Value, Object>> valueToObjectMap;
    static Map<String, Function<Object, Value>> objectToValueMap;
    Map<String, Method> methods;

    public JavaObjectValue(Object obj)
    {
        this.obj = obj;
        if(obj != null) methods = Stream.of(obj.getClass().getMethods()).collect(Collectors.toMap(c -> c.getName(), c -> c));
    }

    static
    {
        valueToObjectMap = Map.ofEntries(
            Map.entry("char", v -> v.toChar()),
            Map.entry("byte", v -> v.toByte()),
            Map.entry("int", v -> v.toInt()),
            Map.entry("long", v -> v.toLong()),
            Map.entry("float", v -> v.toFloat()),
            Map.entry("double", v -> v.toDouble()),

            Map.entry("Character", v -> v.toChar()),
            Map.entry("Byte", v -> v.toByte()),
            Map.entry("Integer", v -> v.toInt()),
            Map.entry("Long", v -> v.toLong()),
            Map.entry("Float", v -> v.toFloat()),
            Map.entry("Double", v -> v.toDouble())
        );
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

    @Override public char toChar()
    {
        if(obj instanceof Character v) return v;
        throw new RuntimeException(String.format("Object %s must have type of char", obj));
    }
    @Override public int toInt()
    {
        if(obj instanceof Number v) return v.intValue();
        throw new RuntimeException(String.format("Object %s must have type of int", obj));
    }
    @Override public long toLong()
    {
        if(obj instanceof Number v) return v.longValue();
        throw new RuntimeException(String.format("Object %s must have type of long", obj));
    }
    @Override public float toFloat()
    {
        if(obj instanceof Number v) return v.floatValue();
        throw new RuntimeException(String.format("Object %s must have type of float", obj));
    }
    @Override public double toDouble()
    {
        if(obj instanceof Number v) return v.doubleValue();
        throw new RuntimeException(String.format("Object %s must have type of double", obj));
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
            Method method = methods.get(s.name.string());
            if(method != null)
            {
                return executableValue(args -> method.invoke(obj, args), method.getParameterTypes());
            }
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

    public static Value executableValue(Executable method, Class<?>[] types)
    {
        return new FunctionValue("[...]", arg ->
        {
            Object[] arguments;
            if(types.length == 0 && arg instanceof NoneValue)
            {
                arguments = new Object[0];
            }
            else if(types.length == 1)
            {
                arguments = new Object[] { fromValue(types[0], arg) };
            }
            else if(arg instanceof ArrayValue a && a.items.length() == types.length)
            {
                arguments = a.items.zip(new ArrayBuffer<>(types), (v, b) -> fromValue(b, v)).toArray(Object[]::new);
            }
            else
            {
                throw new RuntimeException(String.format("Arity mismatch for %s. %i expected", arg.string(), types.length));
            }
            try
            {
                return fromObject(method.call(arguments));
            }
            catch(Throwable ex)
            {
                throw new RuntimeException(ex);
            }
        });
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

    public static Object fromValue(Class<?> type, Value value)
    {
        if(type.isPrimitive())
        {
            var func = valueToObjectMap.get(type.getName());
            if(func != null) return func.apply(value);
        }
        if(type == String.class) return value.string();
        if(type == Void.TYPE) return null;

        if(value instanceof JavaObjectValue job) return job.obj;
        
        return Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] { type }, (p, method, args) ->
        {
            Value varg;
            if(args.length == 0)
            {
                varg = NoneValue.instance;
            }
            else
            {
                varg = new ArrayValue(new ArrayBuffer<Object>(args).map(o -> fromObject(o)));
            }
            return fromValue(method.getReturnType(), value.call(new ArrayValue(varg)));
        });
    }
}