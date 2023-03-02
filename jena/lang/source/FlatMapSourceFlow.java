package jena.lang.source;

public final class FlatMapSourceFlow implements SourceFlow
{
    SourceFlow flow;
    SourceFlatMapping mapping;

    public FlatMapSourceFlow(SourceFlow flow, SourceFlatMapping mapping)
    {
        this.flow = flow;
        this.mapping = mapping;
    }

    @Override
    public void read(SourceAction reader)
    {
        flow.read(source -> mapping.map(source).read(reader));
    }
}