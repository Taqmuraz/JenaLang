package jena.lang.syntax;

import jena.lang.text.TextWriter;
import jena.lang.value.Namespace;
import jena.lang.value.ValueFunction;

public class NoneValueSyntax implements Syntax
{
    @Override
    public ValueFunction value(Namespace namespace)
    {
        return ValueFunction.none;
    }

    @Override
    public void text(TextWriter writer)
    {
        writer.write("()");
    }
}