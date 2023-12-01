package jena.lang.syntax;

import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.Single;
import jena.lang.value.Namespace;
import jena.lang.value.Value;
import jena.lang.value.ValueFunction;

public class IntegerLiteralSyntax implements Syntax
{
    private Text literal;
    private Value value;

    public IntegerLiteralSyntax(Text literal)
    {
        this.literal = literal;
        this.value = Single.of(literal);
    }

    @Override
    public void text(TextWriter writer)
    {
        writer.write(literal);
    }

    @Override
    public ValueFunction value(Namespace namespace)
    {
        return ValueFunction.of(value);
    }
}