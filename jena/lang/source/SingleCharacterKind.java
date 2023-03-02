package jena.lang.source;

public final class SingleCharacterKind implements CharacterKind
{
    private char character;

    public SingleCharacterKind(char character)
    {
        this.character = character;
    }

    @Override
    public boolean isKind(char c)
    {
        return c == character;
    }
}