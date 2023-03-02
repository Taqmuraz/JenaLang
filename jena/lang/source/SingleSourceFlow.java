package jena.lang.source;

public final class SingleSourceFlow implements SourceFlow
{
    private Source source;

    public SingleSourceFlow(Source source)
    {
        this.source = source;
    }

    @Override
    public void read(SourceAction reader)
    {
        reader.call(source);
    }
}