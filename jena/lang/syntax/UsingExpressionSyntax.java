package jena.lang.syntax;

import jena.lang.GenericBuffer;
import jena.lang.source.SingleCharacterSource;
import jena.lang.source.Source;
import jena.lang.source.StringSource;
import jena.lang.value.FlowNamespace;
import jena.lang.value.Namespace;
import jena.lang.value.Value;

public final class UsingExpressionSyntax implements Syntax
{
    private GenericBuffer<Syntax> expressions;
    private GenericBuffer<Syntax> names;
    private Syntax expression;
    
    public UsingExpressionSyntax(GenericBuffer<Syntax> expressions, GenericBuffer<Syntax> names, Syntax expression)
    {
        this.expressions = expressions;
        this.names = names;
        this.expression = expression;
    }

    @Override
    public void source(SyntaxSerializer writer)
    {
        writer.source(new StringSource("using"));
        writer.source(new SingleCharacterSource('('));
        expressions.flow().join(e -> e.source(writer), () -> writer.source(new SingleCharacterSource(',')));
        writer.source(new SingleCharacterSource(')'));
        writer.source(new StringSource("as"));
        writer.source(new SingleCharacterSource('('));
        names.flow().join(n -> n.source(writer), () -> writer.source(new SingleCharacterSource(',')));
        writer.source(new SingleCharacterSource(')'));
        expression.source(writer);
    }

    @Override
    public Syntax decomposed()
    {
        return new UsingExpressionSyntax(expressions.flow().map(e -> e.decomposed()).collect(), names.flow().map(n -> n.decomposed()).collect(), expression.decomposed());
    }

    @Override
    public Value value(Namespace namespace)
    {
        return expression.value(namespace.nested(new FlowNamespace(names.flow().<Source>map(SyntaxSource::new).zip(expressions.flow().map(e -> e.value(namespace))))));
    }
}