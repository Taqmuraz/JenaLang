package jena.lang.syntax;

import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.NumberValue;
import jena.lang.value.Namespace;
import jena.lang.value.Value;

public class IntegerLiteralSyntax implements Syntax
{
    private Text literal;
    private Value value;

    public IntegerLiteralSyntax(Text literal)
    {
        this.literal = literal;
        this.value = new NumberValue(Integer.valueOf(literal.string()));
    }

    @Override
    public void text(TextWriter writer)
    {
        writer.write(literal);
    }

    @Override
    public Value value(Namespace namespace)
    {
        return value;
    }
}