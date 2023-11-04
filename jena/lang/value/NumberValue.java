package jena.lang.value;

import jena.lang.ArrayBuffer;
import jena.lang.GenericBuffer;
import jena.lang.GenericPair;
import jena.lang.StructPair;
import jena.lang.text.StringText;
import jena.lang.text.TextWriter;

public final class NumberValue implements Value, Single
{
    private double value;
    private Value members;

    public NumberValue(double value)
    {
        this.value = value;
        this.members = new SymbolMapValue(symbolValueAction ->
            new ArrayBuffer<String>(new String[]
            {
                "add",    
                "sub",
                "mul",    
                "div",
                "greater",
                "less",
                "equals",
                "notEquals",
                "and",
                "or",
            })
            .flow()
            .zip(new ArrayBuffer<NumberFunction>(new NumberFunction[]
            {
                arg -> value + arg,
                arg -> value - arg,
                arg -> value * arg,
                arg -> value / arg,
                arg -> value > arg ? 1 : 0,
                arg -> value < arg ? 1 : 0,
                arg -> value == arg ? 1 : 0,
                arg -> value != arg ? 1 : 0,
                arg -> (long)value & (long)arg,
                arg -> (long)value | (long)arg,
            })
            .flow()).<GenericPair<String, ValueFunction>>map(p -> action -> p.both((n, f) -> action.call(n, () -> new NumberMethodValue(n, f))))
            .append(new StructPair<String, ValueFunction>("sqrt", () -> new NumberValue((int)Math.sqrt(value))))
            .append(new StructPair<String, ValueFunction>("negative", () -> new NumberValue(-value)))
            .append(new StructPair<String, ValueFunction>("not", () -> new NumberValue(value == 0 ? 1 : 0)))
            .append(new StructPair<String, ValueFunction>("times", () -> new ArrayValue(new GenericBuffer<Value>()
            {
                @Override
                public int length()
                {
                    return (int)value;
                }
                @Override
                public Value at(int index)
                {
                    return new NumberValue(index);
                }
            })
        ))
        .read(p -> p.both(symbolValueAction::call)), arg -> new NumberValue(new ExpressionNumber(arg).single() + value));
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write(new StringText(String.valueOf(value)));
    }
    @Override
    public Value call(Value argument)
    {
        return members.call(argument);
    }

    @Override
    public double single()
    {
        return value;
    }

    @Override
    public boolean valueEquals(Value v)
    {
        return v instanceof Single s && s.single() == value;
    }

    @Override public int toInt()
    {
        return (int)value;
    }
    @Override public byte toByte()
    {
        return (byte)value;
    }
    @Override public long toLong()
    {
        return (long)value;
    }
    @Override public float toFloat()
    {
        return (float)value;
    }
    @Override public double toDouble()
    {
        return value;
    }
}