package jena.syntax;

import jena.lang.source.SingleCharacterSource;

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
}