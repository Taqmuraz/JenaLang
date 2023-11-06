package jena.lang.syntax;

import jena.lang.text.TextWriter;
import jena.lang.value.Namespace;
import jena.lang.value.NoneValue;
import jena.lang.value.Value;

public class NoneValueSyntax implements Syntax
{
    @Override
    public Value value(Namespace namespace)
    {
        return NoneValue.instance;
    }

    @Override
    public void text(TextWriter writer)
    {
        writer.write("()");
    }
}