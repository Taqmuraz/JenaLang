package jena.lang.syntax;

import jena.lang.GenericBuffer;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.EmptyTuple;
import jena.lang.value.Namespace;
import jena.lang.value.Value;

public class ExpressionListSyntax implements Syntax
{
    private GenericBuffer<Syntax> expressions;
    private Text openBrace;
    private Text closeBrace;
    private ValueFactory factory;

    public interface ValueFactory
    {
        Value value(GenericBuffer<Value> expressions);
    }

    public ExpressionListSyntax(GenericBuffer<Syntax> expressions, Text openBrace, Text closeBrace, ValueFactory factory)
    {
        this.expressions = expressions;
        this.openBrace = openBrace;
        this.closeBrace = closeBrace;
        this.factory = factory;
    }

    @Override
    public void text(TextWriter writer)
    {
        new ExpressionListWriter(openBrace, closeBrace).write(expressions, writer);
    }

    @Override
    public Syntax decomposed()
    {
        return new ExpressionListSyntax(expressions.flow().map(a -> a.decomposed()).collect(), openBrace, closeBrace, factory);
    }

    @Override
    public Value value(Namespace namespace)
    {
        if(expressions.length() == 0) return new EmptyTuple();
        return factory.value(expressions.flow().map(a -> a.value(namespace)).collect());
    }
}