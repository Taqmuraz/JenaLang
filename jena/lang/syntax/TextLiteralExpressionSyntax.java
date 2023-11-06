package jena.lang.syntax;

import jena.lang.text.SingleCharacterText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.Namespace;
import jena.lang.value.TextValue;
import jena.lang.value.Value;

public final class TextLiteralExpressionSyntax implements Syntax
{
    private Text text;
    private Value value;

    public TextLiteralExpressionSyntax(Text text)
    {
        this.text = text;
        value = new TextValue(text);
    }

    @Override
    public Value value(Namespace namespace)
    {
        return value;
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