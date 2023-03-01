package jena.lang;

public final class SourceCharacterKindCheck
{
    private int ofKind;
    private int total;

    public SourceCharacterKindCheck(Source source, CharacterKind kind)
    {
        source.read(StartPosition.instance, MaxCount.instance, (c, n) ->
        {
            if (kind.isKind(c)) ofKind++;
            total++;
        });
    }

    public boolean isKind()
    {
        return ofKind == total;
    }
}