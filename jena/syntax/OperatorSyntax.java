package jena.syntax;

import jena.lang.source.SingleCharacterSource;
import jena.lang.source.StringSource;

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
        writer.source(new StringSource("operator("));
        writer.source(new SingleCharacterSource(operator));
        writer.source(new StringSource(")"));
    }
}