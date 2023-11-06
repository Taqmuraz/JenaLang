package jena.lang.value;

import jena.lang.text.StringText;
import jena.lang.text.TextWriter;

public final class NoneValue implements Value
{
    public static final Value instance = new NoneValue();

    @Override
    public void print(TextWriter writer)
    {
        writer.write(new StringText("()"));
    }

    @Override
    public Value call(Value argument)
    {
        return instance;
    }

    @Override
    public boolean valueEquals(Value v)
    {
        return v instanceof NoneValue;   
    }

    @Override
    public Object toObject(Class<?> type)
    {
        return null;
    }

    @Override
    public int valueCode()
    {
        return 0;
    }
}