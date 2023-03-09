package jena.lang.value;

import jena.lang.source.Source;
import jena.lang.source.SourceAction;

public final class TextValue implements Value
{
    private Source text;

    public TextValue(Source text)
    {
        this.text = text;
    }

    @Override
    public void print(SourceAction action)
    {
        action.call(text);
    }

    @Override
    public Value member(Source name)
    {
        return NoneValue.instance;
    }

    @Override
    public Value call(ArgumentList arguments)
    {
        return this;
    }
}