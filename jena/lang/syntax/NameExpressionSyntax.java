package jena.lang.syntax;

import jena.lang.source.Source;
import jena.lang.value.Namespace;
import jena.lang.value.Value;

public final class NameExpressionSyntax implements Syntax
{
    private Source name;

    public NameExpressionSyntax(Source name)
    {
        this.name = name;
    }

    @Override
    public void text(SyntaxSerializer writer)
    {
        writer.source(name);
    }

    @Override
    public Syntax decomposed()
    {
        return this;
    }

    @Override
    public Value value(Namespace namespace)
    {
        return namespace.name(name);
    }
}