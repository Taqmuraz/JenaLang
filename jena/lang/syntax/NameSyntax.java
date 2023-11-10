package jena.lang.syntax;

import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.Namespace;
import jena.lang.value.Value;

public final class NameSyntax implements Syntax
{
    private Text name;

    public NameSyntax(Text name)
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