package jena.lang.text;

public final class EmptyText implements Text
{
    @Override
    public char at(int index)
    {
        return '\0';
    }

    @Override
    public int length()
    {
        return 0;
    }
}