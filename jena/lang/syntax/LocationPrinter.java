package jena.lang.syntax;

import jena.lang.source.SourceAction;
import jena.lang.source.SourceLocation;
import jena.lang.source.StringSource;

public final class LocationPrinter
{
    private SourceLocation location;

    public LocationPrinter(SourceLocation location)
    {
        this.location = location;
    }

    public void print(SourceAction printer)
    {
        location.location(0, (line, symbol) ->
        {
            printer.call(new StringSource("line:"));
            printer.call(new StringSource(String.valueOf(line)));
            printer.call(new StringSource("symbol:"));
            printer.call(new StringSource(String.valueOf(symbol)));
        });
    }
}