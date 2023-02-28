package jena.lang;

import java.util.ArrayList;
import java.util.List;

public final class CharacterSeparatedSourceFlow implements SourceFlow
{
    private SourceFlow flow;
    private int lastStart;
    private int lastIndex;

    public CharacterSeparatedSourceFlow(CharacterKind kind, Source source)
    {
        List<Source> sources = new ArrayList<Source>();
        source.read(StartPosition.instance, MaxCount.instance, (c, n) ->
        {
            if (kind.isKind(c))
            {
                sources.add(new RelativeSource(source, lastStart, n - lastStart));
                lastStart = n + 1;
            }
            lastIndex = n;
        });
        sources.add(new RelativeSource(source, lastStart, lastIndex - lastStart + 1));

        flow = new ArraySourceFlow(sources.toArray(Source[]::new));
    }

    @Override
    public void read(Count count, SourceFlowReader reader)
    {
        flow.read(count, reader);
    }
}