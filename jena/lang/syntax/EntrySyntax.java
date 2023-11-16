package jena.lang.syntax;

import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.FunctionValue;
import jena.lang.value.Namespace;
import jena.lang.value.SingleElementMapValue;
import jena.lang.value.SymbolValue;
import jena.lang.value.ValueFunction;

public final class EntrySyntax implements Syntax
{
    Text name;

    public EntrySyntax(Text name)
    {
        this.name = name;
    }

    @Override
    public ValueFunction value(Namespace namespace)
    {
        return ValueFunction.of(new FunctionValue("value", arg -> new SingleElementMapValue(new SymbolValue(name), arg)));
    }

    @Override
    public void text(TextWriter writer)
    {
        writer.write(name);
        writer.write(":");
    }
}