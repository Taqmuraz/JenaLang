package jena.lang.value;

import jena.lang.GenericBuffer;
import jena.lang.GenericPair;
import jena.lang.StructPair;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.StringText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public final class ClassValue implements Value
{
    private GenericBuffer<Text> arguments;
    private GenericBuffer<GenericPair<Text, ValueProducer>> members;
    private Namespace namespace;
    
    public ClassValue(GenericBuffer<Text> arguments, GenericBuffer<GenericPair<Text, ValueProducer>> members, Namespace namespace)
    {
        this.arguments = arguments;
        this.members = members;
        this.namespace = namespace;
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write(new StringText("class"));
        writer.write(new SingleCharacterText('('));
        arguments.flow().join(a -> writer.write(a), () -> writer.write(new SingleCharacterText(',')));
        writer.write(new SingleCharacterText(')'));
    }

    @Override
    public Value member(Text name)
    {
        return NoneValue.instance;
    }

    @Override
    public Value call(ArgumentList arguments)
    {
        return arguments.number(this.arguments.length(), args ->
        {
            Namespace argumentsSpace = namespace.nested(
                new PairNamespace(this.arguments.flow().zip(args.flow())
                .append(new StructPair<>(new StringText("class"), this)).collect()
            ));
            return new ObjectValue(argumentsSpace, members.map(p -> action -> p.both((name, syntax) -> action.call(name, syntax))));
        },
        () -> NoneValue.instance);
    }
}