package jena.lang.source;

public final class RelativeSourceLocation implements SourceLocation
{
    private SourceLocation anchor;
    private int offset;

    public RelativeSourceLocation(SourceLocation anchor, int offset) {
        this.anchor = anchor;
        this.offset = offset;
    }

    @Override
    public void location(int position, SourceLocationAction action)
    {
        anchor.location(position + offset, action);
    }
}