package jena.lang.source;

public final class StringLiteralFlow implements SourceFlow
{
    private Source source;

    public StringLiteralFlow(Source source)
    {
        this.source = source;
    }

    @Override
    public void read(SourceAction reader)
    {
        CharacterKind separatorKind = c -> c == '\"';
        Source separatorSource = new SingleCharacterSource('\"');

        new SingleCharacterSeparatedSourceFlow(source, separatorKind).notKindFilter(separatorKind).read(new SourceAction()
        {
            int index;

            @Override
            public void call(Source source)
            {
                if((index & 1) == 0) source.split(SpaceCharacterKind.instance).notKindFilter(SpaceCharacterKind.instance).read(reader);
                else
                {
                    reader.call(separatorSource);
                    reader.call(new NonSplitSource(source));
                    reader.call(separatorSource);
                }
                index++;
            }
        });
    }
}