package jena.lang.value;

import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public final class TextValue implements Value
{
    private Text text;

    public TextValue(Text text)
    {
        this.text = text;
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write(text);
    }

    @Override
    public Value member(Text name)
    {
        return NoneValue.instance;
    }

    @Override
    public Value call(ArgumentList arguments)
    {
        return this;
    }
}