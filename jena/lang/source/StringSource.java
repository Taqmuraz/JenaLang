package jena.lang.source;

import jena.lang.Count;
import jena.lang.Position;

public class StringSource implements Source
{
    String source;

    public StringSource(String source)
    {
        this.source = source;
    }

    @Override
    public void read(Position position, Count count, SourceSymbolAction buffer)
    {
        int l = source.length();
        int start = position.position(0, l);
        int c = count.count(l - start);

        for (int i = 0; i < c; i++) buffer.call(source.charAt(i + start), i);
    }
}