package jena.lang.syntax;

import jena.lang.GenericFlow;
import jena.lang.source.SingleCharacterSource;
import jena.lang.value.Namespace;
import jena.lang.value.TupleValue;
import jena.lang.value.Value;

public final class ExpressionListSyntax implements Syntax
{
    private GenericFlow<Syntax> arguments;

    public ExpressionListSyntax(GenericFlow<Syntax> arguments) {
        this.arguments = arguments;
    }

    @Override
    public void source(SyntaxSerializer writer)
    {
        writer.source(new SingleCharacterSource('('));
        arguments.join(arg -> arg.source(writer), () -> writer.source(new SingleCharacterSource(' ')));
        writer.source(new SingleCharacterSource(')'));
    }

    @Override
    public Syntax decomposed()
    {
        return new ExpressionListSyntax(arguments.map(a -> a.decomposed()));
    }

    @Override
    public Value value(Namespace namespace)
    {
        return new TupleValue(arguments.map(a -> a.value(namespace)));
    }
}