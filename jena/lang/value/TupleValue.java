package jena.lang.value;

import jena.lang.GenericBuffer;
import jena.lang.source.SingleCharacterSource;
import jena.lang.source.Source;
import jena.lang.source.SourceAction;

public final class TupleValue implements Value
{
    private GenericBuffer<Value> items;

    public TupleValue(GenericBuffer<Value> items)
    {
        this.items = items;
    }

    @Override
    public void print(SourceAction action)
    {
        action.call(new SingleCharacterSource('('));
        Source separator = new SingleCharacterSource(',');
        
        items.flow().join(item -> item.print(action), () -> action.call(separator));

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
        return arguments.number(0, args -> items.at(items.length() - 1), () -> arguments.number(1, args ->
        {
            Source source = (p, c, a) -> args.at(0).print(s -> s.read(p, c, a));
            return items.at(Integer.valueOf(source.text().toString()));
        }, () -> NoneValue.instance));
    }
}