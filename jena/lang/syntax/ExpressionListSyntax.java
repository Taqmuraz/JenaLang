package jena.lang.syntax;

import jena.lang.EmptyBuffer;
import jena.lang.GenericBuffer;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.Namespace;
import jena.lang.value.Value;
import jena.lang.value.ValueFunction;

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
    public ValueFunction value(Namespace namespace)
    {
        if(expressions.length() == 0) ValueFunction.of(factory.value(new EmptyBuffer<Value>()));

        var exprs = expressions.map(e -> e.value(namespace)).cached();
        return ValueFunction.of(factory.value(exprs.map(e -> e.call()).cached()));
    }
}