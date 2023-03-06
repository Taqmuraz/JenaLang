package jena.lang.value;

import jena.lang.source.Source;
import jena.lang.source.SourceAction;
import jena.lang.source.StringSource;

public final class NoneValue implements Value
{
    public static final Value instance = new NoneValue();

    @Override
    public void print(SourceAction action)
    {
        action.call(new StringSource("none"));
    }

    @Override
    public Value member(Source name)
    {
        return instance;
    }

    @Override
    public Value call(ArgumentList arguments)
    {
        return instance;
    }
}