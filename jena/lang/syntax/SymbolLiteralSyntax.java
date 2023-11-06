package jena.lang.syntax;

import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.Namespace;
import jena.lang.value.SymbolValue;
import jena.lang.value.Value;

public final class SymbolLiteralSyntax implements Syntax
{
    Text name;

    public SymbolLiteralSyntax(Text name)
    {
        this.name = name;
    }

    @Override
    public Value value(Namespace namespace)
    {
        return new SymbolValue(name);
    }

    @Override
    public void text(TextWriter writer)
    {
        writer.write(".");
        writer.write(name);
    }
}