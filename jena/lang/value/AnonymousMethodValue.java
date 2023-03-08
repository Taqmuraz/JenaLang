package jena.lang.value;

import jena.lang.source.Source;
import jena.lang.source.SourceAction;
import jena.lang.source.StringSource;

public final class AnonymousMethodValue implements Value
{
    private int arguments;
    private ValueListFunction function;

    public AnonymousMethodValue(int arguments, ValueListFunction function)
    {
        this.arguments = arguments;
        this.function = function;
    }

    @Override
    public void print(SourceAction action)
    {
        action.call(new StringSource("anonymous_method"));
    }

    @Override
    public Value member(Source name)
    {
        return NoneValue.instance;
    }

    @Override
    public Value call(ArgumentList arguments)
    {
        return arguments.number(this.arguments, function, () -> NoneValue.instance);
    }
}