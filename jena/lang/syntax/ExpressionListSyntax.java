package jena.lang.syntax;

import jena.lang.GenericBuffer;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.Namespace;
import jena.lang.value.TupleValue;
import jena.lang.value.Value;

public final class ExpressionListSyntax implements Syntax
{
    private GenericBuffer<Syntax> expressions;
    private Text openBrace;
    private Text closeBrace;

    public ExpressionListSyntax(GenericBuffer<Syntax> expressions, Text openBrace, Text closeBrace)
    {
        this.expressions = expressions;
        this.openBrace = openBrace;
        this.closeBrace = closeBrace;
    }

    @Override
    public void text(TextWriter writer)
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