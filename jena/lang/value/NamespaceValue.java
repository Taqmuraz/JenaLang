package jena.lang.value;

import jena.lang.GenericBuffer;
import jena.lang.GenericPair;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public final class NamespaceValue implements Value
{
    private Namespace namespace;
    private GenericBuffer<GenericPair<Text, Value>> names;

    public NamespaceValue(GenericBuffer<GenericPair<Text, Value>> names)
    {
        this.names = names;
        namespace = new HashMapNamespace(names);
    }

    @Override
    public void print(TextWriter writer)
    {
        new MemberListPrinter(names).print(writer);
    }

    @Override
    public Value member(Text name)
    {
        return namespace.name(name);
    }

    @Override
    public Value call(ArgumentList arguments)
    {
        return NoneValue.instance;
    }
}