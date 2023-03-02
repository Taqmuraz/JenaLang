package jena.lang.source;

public final class FilterSourceFlow implements SourceFlow
{
    private SourceFlow flow;
    private SourceFilter filter;

    public FilterSourceFlow(SourceFlow flow, SourceFilter filter)
    {
        this.flow = flow;
        this.filter = filter;
    }

    @Override
    public void read(SourceAction reader)
    {
        flow.read(source ->
        {
            if(filter.check(source)) reader.call(source);
        });
    }
}