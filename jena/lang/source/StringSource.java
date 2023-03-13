package jena.lang.source;

import jena.lang.Count;
import jena.lang.Position;
import jena.lang.text.Text;

public class StringSource implements Source
{
    private Text sourceName;
    private String source;

    public StringSource(Text sourceName, String source)
    {
        this.sourceName = sourceName;
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

    @Override
    public SourceLocation location()
    {
        return new CalculatedSourceLocation(sourceName, this);
    }
}