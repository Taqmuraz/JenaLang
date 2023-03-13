package jena.lang.source;

public final class ZeroSourceLocation implements SourceLocation
{
    public ZeroSourceLocation()
    {
    }

    @Override
    public void location(int position, SourceLocationAction action)
    {
        action.call(1, 1);
    }
}