package jena.lang.syntax;

import jena.lang.source.Source;
import jena.lang.source.SourceAction;
import jena.lang.source.StringSource;

public final class KindSourceMistake implements SyntaxMistake
{
    private Source source;
    private Source expectedKind;

    public KindSourceMistake(Source source, Source expectedKind)
    {
        this.source = source;
        this.expectedKind = expectedKind;
    }

    @Override
    public void print(SourceAction printer)
    {
        printer.call(new StringSource("source does not match expected kind:"));
        printer.call(source);
        printer.call(expectedKind);
    }
}