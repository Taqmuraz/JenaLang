package jena.lang.value;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public final class JavaClassValue implements Value
{
    Class<?> javaClass;
    Map<Integer, Constructor<?>> constructors;

    public JavaClassValue(Text path)
    {
        try
        {
            javaClass = Class.forName(path.string());
        }
        catch(Throwable th)
        {
            throw new RuntimeException(th);
        }
        constructors = Stream.of(
            javaClass.getDeclaredConstructors()).collect(
                Collectors.<Constructor<?>, Integer, Constructor<?>>toMap(c -> c.getParameterCount(), c -> c));
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
                return new FunctionValue("argsNumber", number ->
                {
                    int parametersCount = number.toInt();
                    var ctor = constructors.get(parametersCount);
                    if(ctor == null) throw new RuntimeException(String.format("No constructor for %s with %i parameters", javaClass, parametersCount));
                    return JavaObjectValue.executableValue(ctor::newInstance, ctor.getParameterTypes());
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
}