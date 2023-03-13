package jena.lang.syntax;

import jena.lang.source.Source;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.StringText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public final class WrongSourceMistake implements SyntaxMistake
{
    private Source source;

    public WrongSourceMistake(Source source, Text expected)
    {
        this.source = source;
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write(new StringText("wrong source:"));
        writer.write(source.text());
        writer.write(new SingleCharacterText('.'));
    }
}