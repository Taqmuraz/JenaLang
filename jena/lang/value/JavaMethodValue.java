package jena.lang.value;

import java.lang.reflect.Method;

import jena.lang.text.StringText;
import jena.lang.text.Text;
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
    public Value member(Text name)
    {
        return NoneValue.instance;
    }

    @Override
    public Value call(ArgumentList arguments)
    {
        return new TextValue(new StringText(method.toString()));
    }
}