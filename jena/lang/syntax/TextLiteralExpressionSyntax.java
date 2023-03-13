package jena.lang.syntax;

import jena.lang.source.SingleCharacterSource;
import jena.lang.source.Source;
import jena.lang.value.Namespace;
import jena.lang.value.TextValue;
import jena.lang.value.Value;

public final class TextLiteralExpressionSyntax implements Syntax
{
    private Source source;
    private Value value;

    public TextLiteralExpressionSyntax(Source source)
    {
        this.source = source;
        value = new TextValue(source);
    }

    @Override
    public Value value(Namespace namespace)
    {
        return value;
    }

    @Override
    public void text(SyntaxSerializer writer)
    {
        Source separator = new SingleCharacterSource('\"');
        writer.source(separator);
        writer.source(source);
        writer.source(separator);
    }

    @Override
    public Syntax decomposed()
    {
        return this;
    }
}