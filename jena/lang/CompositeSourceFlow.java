package jena.lang;

import java.util.ArrayList;
import java.util.List;

public final class CompositeSourceFlow implements SourceFlow
{
    Iterable<SourceFlow> flows;

    public CompositeSourceFlow(Iterable<SourceFlow> flows)
    {
        this.flows = flows;
    }

    @Override
    public void read(Count count, SourceFlowReader reader)
    {
        List<Source> sources = new ArrayList<Source>();
        int c = count.count(Integer.MAX_VALUE);

        for(SourceFlow flow : flows)
        {
            if(sources.size() < c)
            {
                flow.read(new MinCount(c - sources.size()), reader);
            }
            else break;
        }
    }
}