package jena.lang.source;

public final class MapSourceFlow implements SourceFlow
{
    private SourceFlow flow;
    private SourceMapping mapping;

    public MapSourceFlow(SourceFlow flow, SourceMapping mapping)
    {
        this.flow = flow;
        this.mapping = mapping;
    }

    @Override
    public void read(SourceAction reader)
    {
        flow.read(source -> reader.call(mapping.map(source)));
    }
}