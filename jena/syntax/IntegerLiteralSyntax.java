package jena.syntax;

import jena.lang.source.Source;
import jena.lang.source.StringSource;

public class IntegerLiteralSyntax implements Syntax
{
    private Source literal;

    public IntegerLiteralSyntax(Source literal)
    {
        this.literal = literal;
    }

    @Override
    public void source(SyntaxSerializer writer)
    {
        writer.source(new StringSource("int("));
        writer.source(literal);
        writer.source(new StringSource(")"));
    }
}