package jena.lang.value;

import jena.lang.GenericFunction;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;

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
    public void print(TextWriter writer)
    {
        method.print(writer);
    }

    @Override
    public Value member(Text name)
    {
        return method.member(name);
    }

    @Override
    public Value call(ArgumentList arguments)
    {
        return method.call(arguments);
    }
}