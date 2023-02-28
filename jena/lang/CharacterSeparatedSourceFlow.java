package jena.lang;

import java.util.ArrayList;
import java.util.List;

public final class CharacterSeparatedSourceFlow implements SourceFlow
{
    private SourceFlow flow;
    private StringBuilder text = new StringBuilder();

    public CharacterSeparatedSourceFlow(CharacterKind kind, Source source)
    {
        List<Source> sources = new ArrayList<Source>();
        source.read(c ->
        {
            if (kind.isKind(c))
            {
                if (!text.isEmpty())
                {
                    sources.add(new StringSource(text.toString()));
                    text = new StringBuilder();
                }
            }
            else
            {
                text.append(c);
            }
        });
        if(!text.isEmpty()) sources.add(new StringSource(text.toString()));
        
        flow = new ArraySourceFlow(sources.stream().toArray(Source[]::new));
    }

    @Override
    public void read(Count count, SourceFlowReader reader)
    {
        flow.read(count, reader);
    }
}