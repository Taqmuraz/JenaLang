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
    public Value call(Value argument)
    {
        if(argument instanceof SymbolValue symbol) return namespace.name(symbol.name);
        return NoneValue.instance; 
    }

    @Override
    public boolean valueEquals(Value v)
    {
        return v == this;
    }
}