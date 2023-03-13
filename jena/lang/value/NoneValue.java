package jena.lang.value;

import jena.lang.text.StringText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public final class NoneValue implements Value
{
    public static final Value instance = new NoneValue();

    @Override
    public void print(TextWriter writer)
    {
        writer.write(new StringText("none"));
    }

    @Override
    public Value member(Text name)
    {
        return instance;
    }

    @Override
    public Value call(ArgumentList arguments)
    {
        return instance;
    }
}