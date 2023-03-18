package jena.lang.value;

import jena.lang.SingleBuffer;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public final class IntegerMethodValue implements Value
{
    private Value method;

    public IntegerMethodValue(Text argument, IntegerFunction function)
    {
        method = new MethodValue(new SingleBuffer<Text>(argument), args ->
        {
            Value arg = args.at(0);
            if(arg instanceof IntegerNumber) return new IntegerValue(function.call(((IntegerNumber)arg).integer()));
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