package jena.lang.source;

import jena.lang.Count;
import jena.lang.Position;

public final class EmptySource implements Source
{
    private SourceLocation location;

    public EmptySource(SourceLocation location)
    {
        this.location = location;
    }

    @Override
    public void read(Position position, Count count, SourceSymbolAction buffer)
    {
        
    }

    @Override
    public SourceLocation location()
    {
        return location;
    }
}