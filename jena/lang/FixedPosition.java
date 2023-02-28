package jena.lang;

public final class FixedPosition implements Position
{
    private int position;

    public FixedPosition(int position)
    {
        this.position = position;
    }

    @Override
    public int position(int start, int length)
    {
        return start + position;
    }
}