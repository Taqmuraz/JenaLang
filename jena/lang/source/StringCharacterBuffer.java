package jena.lang.source;

public final class StringCharacterBuffer implements CharacterBuffer
{
    private String string;

    public StringCharacterBuffer(String string)
    {
        this.string = string;
    }

    @Override
    public char at(int index)
    {
        return string.charAt(index);
    }

    @Override
    public int length()
    {
        return string.length();
    }
}