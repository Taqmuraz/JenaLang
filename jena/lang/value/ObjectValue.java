package jena.lang.value;

import jena.lang.GenericBuffer;
import jena.lang.GenericPair;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public final class ObjectValue implements Value
{
    private GenericBuffer<GenericPair<Text, Value>> members;
    private Namespace namespace;

    public ObjectValue(GenericBuffer<GenericPair<Text, Value>> members)
    {
        this.members = members;
        this.namespace = new HashMapNamespace(members);
    }

    @Override
    public void print(TextWriter writer) 
    {
        members.flow().read(p -> p.both((n, v) ->
        {
            writer.write(n);
            writer.write(new SingleCharacterText(';'));
        }));
    }

    @Override
    public Value member(Text name)
    {
        return namespace.name(name);
    }

    @Override
    public Value call(ArgumentList arguments)
    {
        return this;
    }
}