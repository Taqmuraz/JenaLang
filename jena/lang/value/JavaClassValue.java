package jena.lang.value;

import java.lang.reflect.Constructor;
import java.util.List;

import jena.lang.text.TextWriter;

public final class JavaClassValue implements Value
{
    Class<?> javaClass;
    List<Constructor<?>> constructors;

    public JavaClassValue(String path)
    {
        try
        {
            javaClass = Class.forName(path);
        }
        catch(Throwable th)
        {
            throw new RuntimeException(th);
        }
        constructors = List.of(javaClass.getDeclaredConstructors());
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write(javaClass.toString());
    }

    @Override
    public Value call(Value argument)
    {
        if(argument instanceof SymbolValue symbol)
        {
            if(symbol.name.compareString("new"))
            {
                return FunctionValue.parameterizedFunction(
                    "constructor",
                    constructors,
                    javaClass,
                    c -> javaClass,
                    c -> c.getParameterTypes(),
                    c -> c::newInstance);
            }
        }
        return NoneValue.instance;
    }

    @Override
    public boolean valueEquals(Value v)
    {
        return v instanceof JavaClassValue j && j.javaClass == javaClass;
    }

    @Override
    public Object toObject(Class<?> type)
    {
        if(type == Class.class) return javaClass;
        else return new RuntimeException(String.format("%s is not a Class<?> type", type.getName()));
    }
}