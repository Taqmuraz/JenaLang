package jena.lang.value;

import jena.lang.GenericBuffer;
import jena.lang.GenericPair;
import jena.lang.SingleGenericBuffer;
import jena.lang.StructPair;
import jena.lang.source.SingleCharacterSource;
import jena.lang.source.Source;
import jena.lang.source.SourceAction;
import jena.lang.source.StringSource;

public final class TupleValue implements Value
{
    private GenericBuffer<Value> items;
    private ObjectValue members;

    public TupleValue(GenericBuffer<Value> items)
    {
        this.items = items;
        members = new ObjectValue(
            new SingleGenericBuffer<GenericPair<Source, Value>>(
                new StructPair<>(
                    new StringSource("count"),
                    new IntegerValue(items.length()))).flow()
                    .append(
                        new StructPair<>(
                            new StringSource("map"),
                            new AnonymousMethodValue(1, args ->
                                new TupleValue(items.flow().map(item ->
                                args.at(0).call(new SingleArgumentList(item))).collect())))).collect());
    }

    @Override
    public void print(SourceAction action)
    {
        action.call(new SingleCharacterSource('['));
        Source separator = new SingleCharacterSource(',');
        
        items.flow().join(item -> item.print(action), () -> action.call(separator));

        action.call(new SingleCharacterSource(']'));
    }

    @Override
    public Value member(Source name)
    {
        return members.member(name);
    }

    @Override
    public Value call(ArgumentList arguments)
    {
        return arguments.number(0, args -> items.at(items.length() - 1), () -> arguments.number(1, args ->
        {
            Source source = new ValueSource(args.at(0));
            return items.at(Integer.valueOf(source.text().toString()));
        }, () -> NoneValue.instance));
    }
}