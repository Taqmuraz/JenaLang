package jena.lang;

public class RelativeSource implements Source
{
    private Source source;
    private int position;
    private int count;

    public RelativeSource(Source source, int position, int count)
    {
        this.source = source;
        this.position = position;
        this.count = count;
    }

    @Override
    public void read(Position position, Count count, SourceReader reader)
    {
        int c = count.count(this.count);
        source.read(new OffsetPosition(position, this.position), new MinCount(c), reader);
    }
}