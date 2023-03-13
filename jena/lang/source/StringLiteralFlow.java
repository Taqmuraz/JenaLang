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

        new SingleCharacterSeparatedSourceFlow(source, separatorKind).read(new SourceAction()
        {
            int index;

            @Override
            public void call(Source source)
            {
                if(source.isKind(separatorKind))
                {
                    reader.call(source);
                    return;
                }
                if((index & 1) == 0) source.split(SpaceCharacterKind.instance).notKindFilter(SpaceCharacterKind.instance).read(reader);
                else
                {
                    reader.call(new NonSplitSource(source));
                }
                index++;
            }
        });
    }
}