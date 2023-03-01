package jena.lang;

public final class CharacterSeparatedSourceFlow implements SourceFlow
{
    private Source source;
    private CharacterKind kind;

    public CharacterSeparatedSourceFlow(Source source, CharacterKind kind)
    {
        this.source = source;
        this.kind = kind;
    }

    @Override
    public void read(SourceFlowReader reader)
    {
        new SourceFlowReader()
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