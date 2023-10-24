package jena.lang.value;

import jena.lang.text.StringText;
import jena.lang.text.TextWriter;

public final class MethodValue implements Value
{
    public interface RecurCallFunction
    {
        Value call(Value arg, Value self);
    }

    private Value argument;
    private ValueCallFunction function;

    public MethodValue(String argumentName, ValueCallFunction function)
    {
        this.argument = new TextValue(argumentName);
        this.function = function;
    }
    public MethodValue(ValueCallFunction function)
    {
        this.argument = NoneValue.instance;
        this.function = function;
    }
    public MethodValue(String argumentName, RecurCallFunction function)
    {
        this.argument = new TextValue(argumentName);
        this.function = arg -> function.call(arg, this);
    }

    @Override
    public void print(TextWriter writer)
    {
        argument.print(writer::write);
        writer.write(new StringText("->..."));
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