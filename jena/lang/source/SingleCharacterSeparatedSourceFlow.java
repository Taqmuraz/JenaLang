package jena.lang.source;

import jena.lang.MaxCount;
import jena.lang.StartPosition;

public final class SingleCharacterSeparatedSourceFlow implements SourceFlow
{
    private Source source;
    private CharacterKind kind;

    public SingleCharacterSeparatedSourceFlow(Source source, CharacterKind kind)
    {
        this.source = source;
        this.kind = kind;
    }

    @Override
    public void read(SourceAction reader)
    {
        new SourceAction()
        {
            int lastStart;
            int lastIndex;
            @Override
            public void call(Source source)
            {
                source.read(StartPosition.instance, MaxCount.instance, (c, n) ->
                {
                    if (kind.isKind(c))
                    {
                        reader.call(source.subsource(lastStart, n - lastStart));
                        reader.call(source.subsource(n, 1));
                        lastStart = n + 1;
                    }
                    lastIndex = n;
                });
                reader.call(source.subsource(lastStart, lastIndex - lastStart + 1));
            }
        }
        .call(source);
    }
}