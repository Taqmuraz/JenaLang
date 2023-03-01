package jena.lang;

public final class SingleSourceFlow implements SourceFlow
{
    private Source source;

    public SingleSourceFlow(Source source)
    {
        this.source = source;
    }

    @Override
    public void read(SourceFlowReader reader)
    {
        reader.call(source);
    }
}