package jena.lang.syntax;

import jena.lang.GenericFlow;
import jena.lang.source.SingleCharacterSource;
import jena.lang.source.Source;
import jena.lang.source.StringSource;
import jena.lang.value.FlowNamespace;
import jena.lang.value.Namespace;
import jena.lang.value.Value;

public final class UsingExpressionSyntax implements Syntax
{
    private GenericFlow<Syntax> expressions;
    private GenericFlow<Syntax> names;
    private Syntax expression;
    
    public UsingExpressionSyntax(GenericFlow<Syntax> expressions, GenericFlow<Syntax> names, Syntax expression)
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
        expressions.join(e -> e.source(writer), () -> writer.source(new SingleCharacterSource(' ')));
        writer.source(new SingleCharacterSource(')'));
        writer.source(new StringSource("as"));
        writer.source(new SingleCharacterSource('('));
        names.join(n -> n.source(writer), () -> writer.source(new SingleCharacterSource(' ')));
        writer.source(new SingleCharacterSource(')'));
        expression.source(writer);
    }

    @Override
    public Syntax decomposed()
    {
        return new UsingExpressionSyntax(expressions.map(e -> e.decomposed()), names.map(n -> n.decomposed()), expression.decomposed());
    }

    @Override
    public Value value(Namespace namespace)
    {
        return expression.value(namespace.nested(new FlowNamespace(names.<Source>map(SyntaxSource::new).zip(expressions.map(e -> e.value(namespace))))));
    }
}