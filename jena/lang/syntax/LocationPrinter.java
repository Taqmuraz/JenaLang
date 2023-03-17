package jena.lang.syntax;

import jena.lang.source.SourceLocation;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.StringText;
import jena.lang.text.TextWriter;

public final class LocationPrinter
{
    private SourceLocation location;

    public LocationPrinter(SourceLocation location)
    {
        this.location = location;
    }

    public void print(TextWriter writer)
    {
        location.location(0, (origin, line, symbol) ->
        {
            writer.write(new StringText("origin:"));
            writer.write(origin);
            writer.write(new SingleCharacterText('.'));
            writer.write(new StringText("line:"));
            writer.write(new StringText(String.valueOf(line)));
            writer.write(new StringText("symbol:"));
            writer.write(new StringText(String.valueOf(symbol)));
        });
    }
}