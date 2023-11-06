package jena.lang.syntax;

import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.Namespace;
import jena.lang.value.Value;

public final class NameExpressionSyntax implements Syntax
{
    private Text name;

    public NameExpressionSyntax(Text name)
    {
        this.name = name;
    }

    @Override
    public void text(TextWriter writer)
    {
        writer.write(name);
    }

    @Override
    public Value value(Namespace namespace)
    {
        return namespace.name(name);
    }
}