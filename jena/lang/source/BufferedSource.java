package jena.lang.source;

import jena.lang.Count;
import jena.lang.Position;
import jena.lang.text.Text;

public final class BufferedSource implements Source
{
    private Text buffer;
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
    public SourceLocation location()
    {
        return source.location();
    }
}