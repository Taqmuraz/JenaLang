package jena.lang.value;

import jena.lang.source.Source;
import jena.lang.source.SourceAction;

public final class FixedMethodValue implements Value
{
    private Source name;
    private int arguments;
    private ValueListFunction function;

    public FixedMethodValue(Source name, int arguments, ValueListFunction function)
    {
        this.name = name;
        this.arguments = arguments;
        this.function = function;
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
        return arguments.number(this.arguments, function, () -> NoneValue.instance);
    }
}