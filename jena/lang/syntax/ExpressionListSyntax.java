package jena.lang.syntax;

import jena.lang.EmptyBuffer;
import jena.lang.GenericBuffer;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.ArrayValue;
import jena.lang.value.MapValue;
import jena.lang.value.Namespace;
import jena.lang.value.Value;

public class ExpressionListSyntax implements Syntax
{
    private GenericBuffer<Syntax> expressions;
    private Text openBrace;
    private Text closeBrace;
    private ValueFactory factory;

    public static Syntax arraySyntax(GenericBuffer<Syntax> expressions)
    {
        return new ExpressionListSyntax(expressions, Text.of('['), Text.of(']'), ArrayValue::new);
    }
    public static Syntax mapSyntax(GenericBuffer<Syntax> expressions)
    {
        return new ExpressionListSyntax(expressions, Text.of('{'), Text.of('}'), MapValue::new);
    }

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
    public Value value(Namespace namespace)
    {
        if(expressions.length() == 0) factory.value(new EmptyBuffer<Value>());
        return factory.value(expressions.flow().map(a -> a.value(namespace)).collect());
    }
}