package jena.lang.syntax;

import jena.lang.source.Source;
import jena.lang.value.IntegerValue;
import jena.lang.value.Namespace;
import jena.lang.value.Value;

public class IntegerLiteralSyntax implements Syntax
{
    private Source literal;
    private Value value;

    public IntegerLiteralSyntax(Source literal)
    {
        this.literal = literal;
        this.value = new IntegerValue(Integer.valueOf(literal.text().toString()));
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
        return value;
    }
}