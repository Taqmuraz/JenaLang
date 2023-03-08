package jena.lang.syntax;

import jena.lang.GenericBuffer;
import jena.lang.source.SingleCharacterSource;
import jena.lang.source.Source;
import jena.lang.value.Namespace;
import jena.lang.value.TupleValue;
import jena.lang.value.Value;

public final class ExpressionListSyntax implements Syntax
{
    private GenericBuffer<Syntax> arguments;
    private Source openBrace;
    private Source closeBrace;

    public ExpressionListSyntax(GenericBuffer<Syntax> arguments, Source openBrace, Source closeBrace)
    {
        this.arguments = arguments;
        this.openBrace = openBrace;
        this.closeBrace = closeBrace;
    }

    @Override
    public void source(SyntaxSerializer writer)
    {
        writer.source(openBrace);
        arguments.flow().join(arg -> arg.source(writer), () -> writer.source(new SingleCharacterSource(',')));
        writer.source(closeBrace);
    }

    @Override
    public Syntax decomposed()
    {
        return new ExpressionListSyntax(arguments.flow().map(a -> a.decomposed()).collect(), openBrace, closeBrace);
    }

    @Override
    public Value value(Namespace namespace)
    {
        return new TupleValue(arguments.flow().map(a -> a.value(namespace)).collect());
    }
}