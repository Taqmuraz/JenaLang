package jena.lang.value;

import jena.lang.ArrayGenericFlow;
import jena.lang.JoinAction;
import jena.lang.source.SingleCharacterSource;
import jena.lang.source.Source;
import jena.lang.source.SourceAction;

public final class TupleValue implements Value
{
    private Value[] items;

    public TupleValue(Value... items)
    {
        this.items = items;
    }

    @Override
    public void print(SourceAction action)
    {
        action.call(new SingleCharacterSource('('));
        Source space = new SingleCharacterSource(' ');
        
        new JoinAction<Value>(new ArrayGenericFlow<>(items), item -> item.print(action), () -> action.call(space)).join();

        action.call(new SingleCharacterSource(')'));
    }

    @Override
    public Value member(Source name)
    {
        return NoneValue.instance;
    }

    @Override
    public Value call(ArgumentList arguments)
    {
        return arguments.number(0, args -> items[items.length - 1], () -> arguments.number(1, args ->
        {
            Source source = (p, c, a) -> args.at(0).print(s -> s.read(p, c, a));
            return items[Integer.valueOf(source.toString())];
        }, () -> NoneValue.instance));
    }
}