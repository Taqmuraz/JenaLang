package jena.lang;

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
    public void read(SourceFlowReader reader)
    {
        flow.read(source -> mapping.map(source).read(reader));
    }
}