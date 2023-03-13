package jena.lang.source;

public final class StringCharacterKind implements CharacterKind
{
    private String string;
    private int position;

    @Override
    public boolean isKind(char c)
    {
        return position < string.length() && string.charAt(position++) == c;
    }
}