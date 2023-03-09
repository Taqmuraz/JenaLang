package jena.lang.syntax;

import jena.lang.GenericBuffer;
import jena.lang.source.SingleCharacterSource;
import jena.lang.source.StringSource;
import jena.lang.value.Namespace;
import jena.lang.value.NoneValue;
import jena.lang.value.Value;

public final class ClassExpressionSyntax implements Syntax
{
    private GenericBuffer<Syntax> arguments;
    private GenericBuffer<Syntax> members;

    public ClassExpressionSyntax(GenericBuffer<Syntax> arguments, GenericBuffer<Syntax> members)
    {
        this.arguments = arguments;
        this.members = members;
    }
    @Override
    public void source(SyntaxSerializer writer)
    {
        writer.source(new StringSource("class"));
        ExpressionListWriter listWriter = new ExpressionListWriter(new SingleCharacterSource('('), new SingleCharacterSource(')'));
        listWriter.write(arguments, writer);
        listWriter.write(members, writer);
    }
    @Override
    public Syntax decomposed()
    {
        return new ClassExpressionSyntax(
            arguments.flow().map(arg -> arg.decomposed()).collect(),
            members.flow().map(m -> m.decomposed()).collect());
    }
    @Override
    public Value value(Namespace namespace)
    {
        return NoneValue.instance;
    }
}