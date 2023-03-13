package jena.lang.value;

import jena.lang.ArrayBuffer;
import jena.lang.EmptyBuffer;
import jena.lang.GenericBuffer;
import jena.lang.GenericPair;
import jena.lang.StructPair;
import jena.lang.text.StringText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public final class IntegerValue implements Value
{
    private int value;
    private ObjectValue members;

    public IntegerValue(int value)
    {
        this.value = value;
        this.members = new ObjectValue(
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
            })
            .flow().map(n -> new StringText(n))
            .zip(new ArrayBuffer<IntegerFunction>(new IntegerFunction[]
            {
                arg -> value + arg,
                arg -> value - arg,
                arg -> value * arg,
                arg -> value / arg,
                arg -> value > arg ? 1 : 0,
                arg -> value < arg ? 1 : 0,
                arg -> value == arg ? 1 : 0,
                arg -> value != arg ? 1 : 0,
            })
            .flow()).<GenericPair<Text, Value>>map(p -> action -> p.both((n, f) -> action.call(n, new IntegerMethodValue(n, f, i -> i.value))))
            .append(new StructPair<Text, Value>
            (
                new StringText("sqrt"), new MethodValue(new EmptyBuffer<Text>(), args -> new IntegerValue((int)Math.sqrt(value)))
            ))
            .append(new StructPair<Text, Value>
            (
                new StringText("times"), new MethodValue(new EmptyBuffer<Text>(), args ->
                {
                    int times = value;
                    return new TupleValue(new GenericBuffer<Value>()
                    {
                        @Override
                        public int length()
                        {
                            return times;
                        }
                        @Override
                        public Value at(int index)
                        {
                            return new IntegerValue(index);
                        }
                    });
                })
            ))
            .collect());
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write(new StringText(String.valueOf(value)));
    }

    @Override
    public Value member(Text name)
    {
        return members.member(name);
    }

    @Override
    public Value call(ArgumentList arguments)
    {
        return this;
    }
}