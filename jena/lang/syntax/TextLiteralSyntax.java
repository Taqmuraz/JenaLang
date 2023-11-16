package jena.lang.syntax;

import jena.lang.text.SingleCharacterText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.Namespace;
import jena.lang.value.TextValue;
import jena.lang.value.Value;
import jena.lang.value.ValueFunction;

public final class TextLiteralSyntax implements Syntax
{
    private Text text;
    private Value value;

    public TextLiteralSyntax(Text text)
    {
        this.text = text;
        value = new TextValue(text);
    }

    @Override
    public ValueFunction value(Namespace namespace)
    {
        return ValueFunction.of(value);
    }

    @Override
    public void text(TextWriter writer)
    {
        Text separator = new SingleCharacterText('\"');
        writer.write(separator);
        writer.write(text);
        writer.write(separator);
    }
}