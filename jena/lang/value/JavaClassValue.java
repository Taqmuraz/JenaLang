package jena.lang.value;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import jena.lang.text.TextWriter;

public final class JavaClassValue implements Value
{
    Class<?> javaClass;
    List<Constructor<?>> constructors;
    Value classValue;

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
        classValue = new JavaObjectValue(javaClass);
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
            else
            {
                Predicate<Method> modifierCheck = m ->
                {
                    var modifiers = m.getModifiers();
                    return Modifier.isStatic(modifiers);
                };
                var filtered = Stream.of(javaClass.getMethods()).filter(m -> modifierCheck.test(m) && symbol.name.compareString(m.getName())).toList();
                return FunctionValue.parameterizedFunction(
                    symbol.name.string(),
                    filtered,
                    javaClass,
                    m -> m.getReturnType(),
                    m -> m.getParameterTypes(),
                    m -> args -> m.invoke(null, args));
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

    @Override
    public int valueCode()
    {
        return javaClass.hashCode();
    }
}