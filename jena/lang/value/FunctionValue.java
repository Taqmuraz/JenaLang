package jena.lang.value;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import jena.lang.ArrayBuffer;
import jena.lang.text.StringText;
import jena.lang.text.TextWriter;

public final class FunctionValue implements Value
{
    static Map<Class<?>, Class<?>> wrapperMap = Map.ofEntries(
        Map.entry(Integer.TYPE, Integer.class),
        Map.entry(Long.TYPE, Long.class),
        Map.entry(Boolean.TYPE, Boolean.class),
        Map.entry(Character.TYPE, Character.class),
        Map.entry(Byte.TYPE, Byte.class),
        Map.entry(Float.TYPE, Float.class),
        Map.entry(Double.TYPE, Double.class)
    );

    public interface RecurCallFunction
    {
        Value call(Value arg, Value self);
    }

    private Supplier<String> argument;
    private ValueCallFunction function;
    Object hash = new Object();

    public FunctionValue(String argumentName, ValueCallFunction function)
    {
        this.argument = () -> argumentName;
        this.function = function;
    }
    public FunctionValue(Supplier<String> argumentName, ValueCallFunction function)
    {
        this.argument = argumentName;
        this.function = function;
    }
    public FunctionValue(ValueCallFunction function)
    {
        this.argument = NoneValue.instance::string;
        this.function = function;
    }
    public FunctionValue(String argumentName, RecurCallFunction function)
    {
        this.argument = () -> argumentName;
        this.function = arg -> function.call(arg, this);
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write(argument.get());
        writer.write(new StringText("->..."));
    }

    @Override
    public Value call(Value arg)
    {
        return function.call(arg);
    }
    @Override
    public boolean valueEquals(Value v)
    {
        return v == this;
    }
    @Override
    public Object toObject(Class<?> type)
    {
        return Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] { type }, (p, method, args) ->
        {
            Value varg;
            if(args == null || args.length == 0)
            {
                varg = NoneValue.instance;
            }
            else
            {
                varg = new ArrayValue(new ArrayBuffer<Object>(args).map(o -> JavaObjectValue.fromObject(o)));
            }
            var ret = method.getReturnType();
            var result = function.call(new ArrayValue(varg));
            if(ret == Void.TYPE)
            {
                return null;
            }
            return result.toObject(ret);
        });
    }

    static boolean assignabilityCheck(Class<?> from, Class<?> to)
    {
        if(from == to) return true;
        if(from.isPrimitive())
        {
            return wrapperMap.get(from).isAssignableFrom(to);
        }
        else if(to.isPrimitive())
        {
            return from.isAssignableFrom(wrapperMap.get(to));
        }
        else return to.isAssignableFrom(from);
    }

    public static <Executable> Value parameterizedFunction(String name, List<Executable> executables, Class<?> declaredType, Function<Executable, Class<?>> returnType, Function<Executable, Class<?>[]> parameters, Function<Executable, JavaFunctionValue.Function> function)
    {
        return new FunctionValue("[parameterTypes]", types ->
        {
            if(types instanceof ArrayValue parameterTypes)
            {
                var ctor = executables.stream().filter(c ->
                {
                    var ctorParameters = parameters.apply(c);
                    if(ctorParameters.length != parameterTypes.items.length()) return false;
                    for(int i = 0; i < ctorParameters.length; i++)
                    {
                        if(!assignabilityCheck((Class<?>)parameterTypes.items.at(i).toObject(Class.class), ctorParameters[i]))
                        {
                            return false;
                        }
                    }
                    return true;
                })
                .findFirst();
                if(!ctor.isPresent()) throw new RuntimeException(String.format("No %s declared in %s with %s parameters", name, declaredType.getName(), types.string()));
                return new JavaFunctionValue(parameters.apply(ctor.get()), returnType.apply(ctor.get()), function.apply(ctor.get()));
            }
            throw new RuntimeException("Array of java.lang.Class is expected");
        });
    }
    @Override
    public int valueCode()
    {
        return hash.hashCode();
    }
}