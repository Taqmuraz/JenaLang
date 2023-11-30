package jena.lang.syntax;

import jena.lang.text.TextWriter;
import jena.lang.value.FunctionValue;
import jena.lang.value.Namespace;
import jena.lang.value.SingleElementMapValue;
import jena.lang.value.ValueFunction;

public final class EntrySyntax implements Syntax
{
    Syntax key;

    public EntrySyntax(Syntax key)
    {
        this.key = key;
    }

    @Override
    public ValueFunction value(Namespace namespace)
    {
        var keyValue = key.value(namespace);
        return ValueFunction.of(new FunctionValue("value", arg -> new SingleElementMapValue(keyValue.call(), arg)));
    }

    @Override
    public void text(TextWriter writer)
    {
        key.text(writer);
        writer.write(":");
    }
}