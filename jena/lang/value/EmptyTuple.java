package jena.lang.value;

import jena.lang.EmptyBuffer;
import jena.lang.GenericBuffer;
import jena.lang.text.StringText;
import jena.lang.text.TextWriter;

public final class EmptyTuple implements Value
{
    @Override
    public void print(TextWriter writer)
    {
        writer.write(new StringText("()"));
    }

    @Override
    public Value call(Value argument)
    {
        return NoneValue.instance;
    }

    @Override
    public GenericBuffer<Value> decompose()
    {
        return new EmptyBuffer<Value>();
    }

    @Override
    public boolean valueEquals(Value v)
    {
        return v.decompose().length() == 0;
    }
}