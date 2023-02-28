package jena.lang;

public final class SingleSourceFlow implements SourceFlow
{
    private Source source;

    public SingleSourceFlow(Source source)
    {
        this.source = source;
    }

    @Override
    public void read(Count count, SourceFlowReader reader)
    {
        int c = count.count(1);
        if (c > 0) reader.call(source);
    }
}