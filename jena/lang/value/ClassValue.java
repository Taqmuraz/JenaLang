package jena.lang.value;

import jena.lang.GenericBuffer;
import jena.lang.GenericPair;
import jena.lang.source.Source;
import jena.lang.source.SourceAction;
import jena.lang.source.StringSource;

public final class ClassValue implements Value
{
    private GenericBuffer<Source> arguments;
    private GenericBuffer<GenericPair<Source, ValueProducer>> members;
    private Namespace namespace;
    
    public ClassValue(GenericBuffer<Source> arguments, GenericBuffer<GenericPair<Source, ValueProducer>> members, Namespace namespace)
    {
        this.arguments = arguments;
        this.members = members;
        this.namespace = namespace;
    }

    @Override
    public void print(SourceAction action)
    {
        action.call(new StringSource("class"));
    }

    @Override
    public Value member(Source name)
    {
        return NoneValue.instance;
    }

    @Override
    public Value call(ArgumentList arguments)
    {
        return arguments.number(this.arguments.length(), args ->
        {
            Namespace argumentsSpace = namespace.nested(new PairNamespace(this.arguments.flow().zip(args.flow()).collect()));
            return new ObjectValue(members.map(p -> action -> p.both((name, syntax) -> action.call(name, syntax.value(argumentsSpace)))));
        },
        () -> NoneValue.instance);
    }
}