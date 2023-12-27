package jena.lang.source;

import jena.lang.syntax.LocationPrinter;

public interface SourceLocation
{
    void location(int position, SourceLocationAction action);

    default String string()
    {
        var sb = new StringBuilder();
        new LocationPrinter(this).print(t -> sb.append(t.string()));
        return sb.toString();
    }
}