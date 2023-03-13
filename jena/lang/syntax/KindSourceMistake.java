package jena.lang.syntax;

import jena.lang.source.Source;
import jena.lang.text.StringText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public final class KindSourceMistake implements SyntaxMistake
{
    private Source source;
    private Text expectedKind;

    public KindSourceMistake(Source source, Text expectedKind)
    {
        this.source = source;
        this.expectedKind = expectedKind;
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write(new StringText("source does not match expected kind."));
        writer.write(new StringText("source:"));
        writer.write(source.text());
        writer.write(new StringText("expected kind:"));
        writer.write(expectedKind);
    }
}