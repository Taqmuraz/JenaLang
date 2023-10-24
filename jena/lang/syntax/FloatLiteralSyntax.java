package jena.lang.syntax;

import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.Namespace;
import jena.lang.value.NumberValue;
import jena.lang.value.Value;

public class FloatLiteralSyntax implements Syntax
{
    private Text literal;
    private Value value;

    public FloatLiteralSyntax(Text literal)
    {
        this.literal = literal;
        this.value = new NumberValue(Float.valueOf(literal.string()));
    }

    @Override
    public void text(TextWriter writer)
    {
        writer.write(literal);
    }

    @Override
    public Syntax decomposed()
    {
        return this;
    }

    @Override
    public Value value(Namespace namespace)
    {
        return value;
    }
}