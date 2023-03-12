package jena.lang.source;

import jena.lang.Count;
import jena.lang.Position;

public final class EmptySource implements Source
{
    public static final Source instance = new EmptySource();

    @Override
    public void read(Position position, Count count, SourceSymbolAction buffer)
    {
        
    }

    @Override
    public SourceLocation location(int position)
    {
        return new WrongSourceLocation();
    }
}