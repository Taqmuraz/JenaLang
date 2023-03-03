package jena.syntax;

import jena.lang.source.SingleCharacterSource;

public final class OperatorSyntax implements Syntax
{
    private char operator;

    public OperatorSyntax(char operator)
    {
        this.operator = operator;
    }
    @Override
    public void source(SyntaxSerializer writer)
    {
        writer.source(new SingleCharacterSource(operator));
    }
}