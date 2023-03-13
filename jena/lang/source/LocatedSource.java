package jena.lang.source;

import jena.lang.Count;
import jena.lang.Position;

public final class LocatedSource implements Source
{
    private Source source;
    private SourceLocation location;

    public LocatedSource(Source source, SourceLocation location)
    {
        this.source = source;
        this.location = location;
    }

    @Override
    public void read(Position position, Count count, SourceSymbolAction buffer)
    {
        source.read(position, count, buffer);
    }

    @Override
    public SourceLocation location()
    {
        return location;
    }
}