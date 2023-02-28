package jena.lang;

import java.util.ArrayList;
import java.util.List;

public final class StringLiteralFlow implements SourceFlow
{
    private SourceFlow flow;

    public StringLiteralFlow(Source source)
    {
        List<SourceFlow> flows = new ArrayList<SourceFlow>();
        new CharacterSeparatedSourceFlow(c -> c == '\"', source).read(MaxCount.instance, new SourceFlowReader()
        {
            int index;

            @Override
            public void call(Source source)
            {
                if((index & 1) == 0) flows.add(new CharacterSeparatedSourceFlow(Character::isWhitespace, source));
                else flows.add(new SingleSourceFlow(source));
                index++;
            }
        });
        this.flow = new CompositeSourceFlow(flows);
    }

    @Override
    public void read(Count count, SourceFlowReader reader)
    {
        flow.read(count, reader);
    }
}