package jena.syntax;

import jena.lang.source.Source;
import jena.lang.value.IntegerValue;
import jena.lang.value.Namespace;
import jena.lang.value.Value;

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
        writer.source(literal);
    }

    @Override
    public Syntax decomposed()
    {
        return this;
    }

    @Override
    public Value value(Namespace namespace)
    {
        return new IntegerValue(Integer.valueOf(literal.text().toString()));
    }
}