package jena.lang.source;

public final class SpaceCharacterKind implements CharacterKind
{
    public static final CharacterKind instance = new SpaceCharacterKind();

    @Override
    public boolean isKind(char c)
    {
        return Character.isWhitespace(c);
    }
}