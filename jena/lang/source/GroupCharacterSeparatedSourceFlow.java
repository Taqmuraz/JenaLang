package jena.lang.source;

import jena.lang.MaxCount;
import jena.lang.StartPosition;

public final class GroupCharacterSeparatedSourceFlow implements SourceFlow
{
    private Source source;
    private CharacterKind kind;

    public GroupCharacterSeparatedSourceFlow(Source source, CharacterKind kind)
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
            boolean inKind;
            @Override
            public void call(Source source)
            {
                source.read(StartPosition.instance, MaxCount.instance, (c, n) ->
                {
                    boolean kindCheck = kind.isKind(c);
                    if(inKind != kindCheck)
                    {
                        inKind = kindCheck;
                        reader.call(source.subsource(lastStart, n - lastStart));
                        lastStart = n;
                    }
                    lastIndex = n;
                });
                reader.call(source.subsource(lastStart, lastIndex - lastStart + 1));
            }
        }
        .call(source);
    }
}