package jena.lang.value;

import jena.lang.source.Source;
import jena.lang.source.SourceAction;

public final class MethodValue implements Value
{
    public interface Call
    {
        Value call(ArgumentList arguments);
    }

    private Source name;
    private Call call;

    public MethodValue(Source name, Call call)
    {
        this.name = name;
        this.call = call;
    }
    @Override
    public void print(SourceAction action)
    {
        action.call(name);
    }
    @Override
    public Value member(Source name)
    {
        return NoneValue.instance;
    }
    @Override
    public Value call(ArgumentList arguments)
    {
        return call.call(arguments);
    }
}