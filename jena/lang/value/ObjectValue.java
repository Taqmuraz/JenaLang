package jena.lang.value;

import jena.lang.GenericBuffer;
import jena.lang.GenericPair;
import jena.lang.source.SingleCharacterSource;
import jena.lang.source.Source;
import jena.lang.source.SourceAction;

public final class ObjectValue implements Value
{
    private GenericBuffer<GenericPair<Source, Value>> members;
    private Namespace namespace;

    public ObjectValue(GenericBuffer<GenericPair<Source, Value>> members)
    {
        this.members = members;
        this.namespace = new HashMapNamespace(members);
    }

    @Override
    public void print(SourceAction action) 
    {
        members.flow().read(p -> p.both((n, v) ->
        {
            action.call(n);
            action.call(new SingleCharacterSource(';'));
        }));
    }

    @Override
    public Value member(Source name)
    {
        return namespace.name(name);
    }

    @Override
    public Value call(ArgumentList arguments)
    {
        return this;
    }
}