package jena.lang.value;

import jena.lang.GenericFunction;
import jena.lang.source.Source;
import jena.lang.source.SourceAction;

public final class IntegerMethodValue implements Value
{
    private Value method;

    public IntegerMethodValue(IntegerFunction function, GenericFunction<IntegerValue, Integer> value)
    {
        method = new AnonymousMethodValue(1, args ->
        {
            Value arg = args.at(0);
            if(arg instanceof IntegerValue) return new IntegerValue(function.call(value.call((IntegerValue)arg)));
            else return NoneValue.instance;
        });
    }

    @Override
    public void print(SourceAction action)
    {
        method.print(action);
    }

    @Override
    public Value member(Source name)
    {
        return method.member(name);
    }

    @Override
    public Value call(ArgumentList arguments)
    {
        return method.call(arguments);
    }
}