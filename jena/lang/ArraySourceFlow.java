package jena.lang;

public final class ArraySourceFlow implements SourceFlow
{
    private Source[] sources;

    public ArraySourceFlow(Source... sources)
    {
        this.sources = sources;
    }

    @Override
    public void read(SourceFlowReader reader)
    {
        for(int i = 0; i < sources.length; i++) reader.call(sources[i]);
    }
}