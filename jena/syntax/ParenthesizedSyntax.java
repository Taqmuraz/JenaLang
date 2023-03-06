package jena.syntax;

import jena.lang.source.SingleCharacterSource;
import jena.lang.value.Namespace;
import jena.lang.value.Value;

public final class ParenthesizedSyntax implements Syntax
{
    private Syntax expression;

    public ParenthesizedSyntax(Syntax expression)
    {
        this.expression = expression;
    }

    @Override
    public void source(SyntaxSerializer writer)
    {
        writer.source(new SingleCharacterSource('('));
        expression.source(writer);
        writer.source(new SingleCharacterSource(')'));
    }

    @Override
    public Syntax decomposed()
    {
        return new ParenthesizedSyntax(expression.decomposed());
    }

    @Override
    public Value value(Namespace namespace)
    {
        return expression.value(namespace);
    }
}