package jena.lang.value;

import jena.lang.text.SingleCharacterText;
import jena.lang.text.StringText;
import jena.lang.text.TextWriter;

public final class MethodValue implements Value
{
    private Value argument;
    private ValueCallFunction function;

    public MethodValue(Value argument, ValueCallFunction function)
    {
        this.argument = argument;
        this.function = function;
    }
    public MethodValue(ValueCallFunction function)
    {
        this.argument = new EmptyTuple();
        this.function = function;
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write(new StringText("method"));
        writer.write(new SingleCharacterText('('));
        argument.print(writer::write);
        writer.write(new SingleCharacterText(')'));
    }

    @Override
    public Value call(Value arg)
    {
        return function.call(arg);
    }
    @Override
    public boolean valueEquals(Value v)
    {
        return v == this;
    }
}