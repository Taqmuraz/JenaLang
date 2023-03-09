package jena.lang.value;

import jena.lang.ArrayGenericBuffer;
import jena.lang.GenericBuffer;
import jena.lang.GenericPair;
import jena.lang.StructPair;
import jena.lang.source.Source;
import jena.lang.source.SourceAction;
import jena.lang.source.StringSource;

public final class IntegerValue implements Value
{
    private int value;
    private ObjectValue members;

    public IntegerValue(int value)
    {
        this.value = value;
        this.members = new ObjectValue(
            new ArrayGenericBuffer<String>(new String[]
            {
                "add",    
                "sub",    
                "mul",    
                "div",    
            })
            .flow().map(n -> new StringSource(n))
            .zip(new ArrayGenericBuffer<IntegerFunction>(new IntegerFunction[]
            {
                arg -> value + arg,
                arg -> value - arg,
                arg -> value * arg,
                arg -> value / arg,
            })
            .flow()).<GenericPair<Source, Value>>map(p -> action -> p.both((n, f) -> action.call(n, new IntegerMethodValue(f, i -> i.value))))
            .append(new StructPair<Source, Value>
            (
                new StringSource("sqrt"), new AnonymousMethodValue(0, args -> new IntegerValue((int)Math.sqrt(value)))
            ))
            .append(new StructPair<Source, Value>
            (
                new StringSource("times"), new AnonymousMethodValue(1, args ->
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
                            return args.at(0).call(new SingleArgumentList(new IntegerValue(index)));
                        }
                    }.flow().collect());
                })
            ))
            .collect());
    }

    @Override
    public void print(SourceAction action)
    {
        action.call(new StringSource(String.valueOf(value)));
    }

    @Override
    public Value member(Source name)
    {
        return members.member(name);
    }

    @Override
    public Value call(ArgumentList arguments)
    {
        return this;
    }
}