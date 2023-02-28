package jena.lang;

public final class OffsetPosition implements Position
{
    private Position base;
    private int offset;

    public OffsetPosition(Position base, int offset)
    {
        this.base = base;
        this.offset = offset;
    }

    @Override
    public int position(int start, int length)
    {
        return base.position(start, length) + offset;
    }
}