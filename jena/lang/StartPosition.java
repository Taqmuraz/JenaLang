package jena.lang;

public final class StartPosition implements Position
{
    public static final Position instance = new StartPosition();

    @Override
    public int position(int start, int length)
    {
        return start;
    }
}