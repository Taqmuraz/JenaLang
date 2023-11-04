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
                return new FunctionValue("[parameterTypes]", types ->
                {
                    if(types instanceof ArrayValue parameterTypes)
                    {
                        var ctor = constructors.stream().filter(c ->
                        {
                            var ctorParameters = c.getParameterTypes();
                            if(ctorParameters.length != parameterTypes.items.length()) return false;
                            for(int i = 0; i < ctorParameters.length; i++)
                            {
                                if(ctorParameters[i] != parameterTypes.items.at(i).toObject(Class.class))
                                {
                                    return false;
                                }
                            }
                            return true;
                        })
                        .findFirst();
                        if(!ctor.isPresent()) throw new RuntimeException(String.format("No constructor for %s with %s parameters", javaClass, types.string()));
                        return new JavaFunctionValue(ctor.get().getParameterTypes(), javaClass, ctor.get()::newInstance);
                    }
                    throw new RuntimeException("Array of java.lang.Class is expected");
                });
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