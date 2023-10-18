package jena.lang.value;

import jena.lang.GenericBuffer;
import jena.lang.GenericPair;
import jena.lang.text.StringText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public final class JenaObjectValue implements Value
{
    private GenericBuffer<GenericPair<Text, Value>> members;
    private Namespace namespace;

    public JenaObjectValue(Namespace namespace, GenericBuffer<GenericPair<Text, ValueProducer>> members)
    {
        Namespace arguments = namespace.nested(new SingleNamespace(new StringText("this"), this));
        this.members = members.map(p -> action -> p.both((name, producer) -> action.call(name, producer.value(arguments))));
        this.namespace = new HashMapNamespace(this.members);
    }

    @Override
    public void print(TextWriter writer) 
    {
        new MemberListPrinter(members).print(writer);
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