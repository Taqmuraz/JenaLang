package jena.lang.value;

import java.lang.reflect.Proxy;

import jena.lang.ArrayBuffer;
import jena.lang.ArrayGenericFlow;
import jena.lang.text.TextWriter;

public final class JavaFunctionValue implements Value
{
    public interface Function
    {
        Object apply(Object[] args) throws Throwable;
    }

    Class<?>[] parameterTypes;
    Class<?> returnType;
    Function function;

    public JavaFunctionValue(Class<?>[] parameterTypes, Class<?> returnType, Function function)
    {
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
        this.function = function;
    }

    @Override
    public void print(TextWriter writer)
    {
        if(parameterTypes.length == 0)
        {
            writer.write("() -> ");
        }
        else if(parameterTypes.length == 1)
        {
            writer.write(parameterTypes[0].getName());
            writer.write(" -> ");
        }
        else
        {
            writer.write("[");
            new ArrayGenericFlow<>(parameterTypes).join(p -> writer.write(p.getName()), () -> writer.write(", "));
            writer.write("] -> ");
        }
        writer.write(returnType.getName());
    }

    @Override
    public Value call(Value arg)
    {
        Object[] arguments;
        if(parameterTypes.length == 0 && arg instanceof NoneValue)
        {
            arguments = new Object[0];
        }
        else if(parameterTypes.length == 1)
        {
            arguments = new Object[] { arg.toObject(parameterTypes[0]) };
        }
        else if(arg instanceof ArrayValue a && a.items.length() == parameterTypes.length)
        {
            arguments = a.items
                .zip(new ArrayBuffer<>(parameterTypes), (v, t) -> v.toObject(t))
                .toArray(Object[]::new);
        }
        else
        {
            throw new RuntimeException(String.format("Arity mismatch for %s. %d expected", arg.string(), parameterTypes.length));
        }
        try
        {
            return JavaObjectValue.fromObject(function.apply(arguments));
        }
        catch(Throwable ex)
        {
            throw new RuntimeException(ex);
        }
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
            return function.apply(args);
        });
    }
}