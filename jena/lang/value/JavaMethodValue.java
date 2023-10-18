package jena.lang.value;

import java.lang.reflect.Method;

import jena.lang.text.StringText;
import jena.lang.text.TextWriter;

public final class JavaMethodValue implements Value
{
    Method method;

    public JavaMethodValue(Method method)
    {
        this.method = method;
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write(new StringText(method.toString()));
    }

    @Override
    public Value call(Value argument)
    {
        return new TextValue(new StringText(method.toString()));
    }

    @Override
    public boolean valueEquals(Value v)
    {
        return v instanceof JavaMethodValue m && m.method.equals(method);
    }
}