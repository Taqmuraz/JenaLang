package jena.lang.value;

import jena.lang.text.TextWriter;

public final class NumberMethodValue implements Value
{
    private Value method;

    public NumberMethodValue(String argumentName, NumberFunction function)
    {
        method = new MethodValue("number", arg ->
        {
            if(arg instanceof Single) return new NumberValue(function.call(((Single)arg).single()));
            else return NoneValue.instance;
        });
    }

    @Override
    public void print(TextWriter writer)
    {
        method.print(writer);
    }

    @Override
    public Value call(Value argument)
    {
        return method.call(argument);
    }

    @Override
    public boolean valueEquals(Value v)
    {
        return v == this;
    }
}