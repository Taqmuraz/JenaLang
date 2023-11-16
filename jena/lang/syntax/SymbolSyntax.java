package jena.lang.syntax;

import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.Namespace;
import jena.lang.value.SymbolValue;
import jena.lang.value.ValueFunction;

public final class SymbolSyntax implements Syntax
{
    Text name;

    public SymbolSyntax(Text name)
    {
        this.name = name;
    }

    @Override
    public ValueFunction value(Namespace namespace)
    {
        return ValueFunction.of(new SymbolValue(name));
    }

    @Override
    public void text(TextWriter writer)
    {
        writer.write(".");
        writer.write(name);
    }
}