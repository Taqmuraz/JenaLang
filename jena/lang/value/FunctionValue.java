package jena.lang.value;

import jena.lang.text.StringText;
import jena.lang.text.TextWriter;

public final class FunctionValue implements Value
{
    public interface RecurCallFunction
    {
        Value call(Value arg, Value self);
    }

    private Value argument;
    private ValueCallFunction function;

    public FunctionValue(String argumentName, ValueCallFunction function)
    {
        this.argument = new TextValue(argumentName);
        this.function = function;
    }
    public FunctionValue(ValueCallFunction function)
    {
        this.argument = NoneValue.instance;
        this.function = function;
    }
    public FunctionValue(String argumentName, RecurCallFunction function)
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