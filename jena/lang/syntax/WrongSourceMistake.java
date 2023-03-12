package jena.lang.syntax;

import jena.lang.source.Source;
import jena.lang.source.SourceAction;
import jena.lang.source.StringSource;

public final class WrongSourceMistake implements SyntaxMistake
{
    private Source source;
    //private Source expected;

    public WrongSourceMistake(Source source, Source expected)
    {
        this.source = source;
        //this.expected = expected;
    }

    @Override
    public void print(SourceAction printer)
    {
        printer.call(new StringSource("wrong source:"));
        printer.call(source);
        //printer.call(new StringSource("expected:"));
        //printer.call(expected);
    }
}