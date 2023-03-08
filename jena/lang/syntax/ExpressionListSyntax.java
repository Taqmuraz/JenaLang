package jena.lang.syntax;

import jena.lang.GenericFlow;
import jena.lang.source.SingleCharacterSource;
import jena.lang.source.Source;
import jena.lang.value.Namespace;
import jena.lang.value.TupleValue;
import jena.lang.value.Value;

public final class ExpressionListSyntax implements Syntax
{
    private GenericFlow<Syntax> arguments;
    private Source openBrace;
    private Source closeBrace;

    public ExpressionListSyntax(GenericFlow<Syntax> arguments, Source openBrace, Source closeBrace)
    {
        this.arguments = arguments;
        this.openBrace = openBrace;
        this.closeBrace = closeBrace;
    }

    @Override
    public void source(SyntaxSerializer writer)
    {
        writer.source(openBrace);
        arguments.join(arg -> arg.source(writer), () -> writer.source(new SingleCharacterSource(',')));
        writer.source(closeBrace);
    }

    @Override
    public Syntax decomposed()
    {
        return new ExpressionListSyntax(arguments.map(a -> a.decomposed()), openBrace, closeBrace);
    }

    @Override
    public Value value(Namespace namespace)
    {
        return new TupleValue(arguments.map(a -> a.value(namespace)));
    }
}