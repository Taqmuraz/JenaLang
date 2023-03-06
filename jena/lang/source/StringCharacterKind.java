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

    public Source charactersLeft()
    {
        if(position < string.length()) return new StringSource(string.substring(position));
        return new EmptySource();
    }
}