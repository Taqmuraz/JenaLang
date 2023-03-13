package jena.lang.source;

import jena.lang.Count;
import jena.lang.Position;

public final class NonSplitSource implements Source
{
    private Source source;

    public NonSplitSource(Source source)
    {
        this.source = source;
    }

    @Override
    public void read(Position position, Count count, SourceSymbolAction buffer)
    {
        source.read(position, count, buffer);
    }

    @Override
    public SourceFlow flow(SourceFlatMapping mapping)
    {
        return new SingleSourceFlow(this);
    }

    @Override
    public SourceLocation location()
    {
        return source.location();
    }
}