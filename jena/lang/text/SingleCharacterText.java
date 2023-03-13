package jena.lang.text;

public final class SingleCharacterText implements Text
{
    private char character;

    public SingleCharacterText(char character)
    {
        this.character = character;
    }

    @Override
    public char at(int index)
    {
        return character;
    }

    @Override
    public int length()
    {
        return 1;
    }
}