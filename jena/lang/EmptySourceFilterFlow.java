package jena.lang;

import java.util.ArrayList;
import java.util.List;

public final class EmptySourceFilterFlow implements SourceFlow
{
    private SourceFlow flow;

    public EmptySourceFilterFlow(SourceFlow flow)
    {
        List<Source> sources = new ArrayList<Source>();
        flow.read(MaxCount.instance, s ->
        {
            if (!new EmptySourceCheck(s).isEmpty()) sources.add(s);
        });
        this.flow = new ArraySourceFlow(sources.toArray(Source[]::new));
    }

    @Override
    public void read(Count count, SourceFlowReader reader)
    {
        flow.read(count, reader);
    }
}