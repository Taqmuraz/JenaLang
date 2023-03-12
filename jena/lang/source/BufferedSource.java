package jena.lang.source;

import jena.lang.Count;
import jena.lang.Position;

public final class BufferedSource implements Source
{
    private CharacterBuffer buffer;
    private Source source;

    public BufferedSource(Source source)
    {
        this.buffer = source.text();
        this.source = source;
    }

    @Override
    public void read(Position position, Count count, SourceSymbolAction action)
    {
        int c = count.count(buffer.length());
        int p = position.position(0, c);
        for(int i = 0; i < c; i++) action.call(buffer.at(i + p), i);
    }

    @Override
    public String toString()
    {
        return buffer.toString();
    }

    @Override
    public SourceLocation location(int position)
    {
        return source.location(position);
    }
}