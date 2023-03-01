package jena.lang;

public final class StringLiteralFlow implements SourceFlow
{
    private Source source;

    public StringLiteralFlow(Source source)
    {
        this.source = source;
    }

    @Override
    public void read(SourceFlowReader reader)
    {
        CharacterKind separatorKind = c -> c == '\"';
        CharacterKind whitespaceKind = Character::isWhitespace;

        new CharacterSeparatedSourceFlow(source, separatorKind).notKindFilter(separatorKind).read(new SourceFlowReader()
        {
            int index;

            @Override
            public void call(Source source)
            {
                if((index & 1) == 0) source.split(whitespaceKind).notKindFilter(whitespaceKind).read(reader);
                else reader.call(source);
                index++;
            }
        });
    }
}