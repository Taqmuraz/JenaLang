package jena.lang.syntax;

import jena.lang.GenericBuffer;
import jena.lang.source.Source;
import jena.lang.value.Namespace;
import jena.lang.value.TupleValue;
import jena.lang.value.Value;

public final class ExpressionListSyntax implements Syntax
{
    private GenericBuffer<Syntax> expressions;
    private Source openBrace;
    private Source closeBrace;

    public ExpressionListSyntax(GenericBuffer<Syntax> expressions, Source openBrace, Source closeBrace)
    {
        this.expressions = expressions;
        this.openBrace = openBrace;
        this.closeBrace = closeBrace;
    }

    @Override
    public void source(SyntaxSerializer writer)
    {
        new ExpressionListWriter(openBrace, closeBrace).write(expressions, writer);
    }

    @Override
    public Syntax decomposed()
    {
        return new ExpressionListSyntax(expressions.flow().map(a -> a.decomposed()).collect(), openBrace, closeBrace);
    }

    @Override
    public Value value(Namespace namespace)
    {
        return new TupleValue(expressions.flow().map(a -> a.value(namespace)).collect());
    }
}