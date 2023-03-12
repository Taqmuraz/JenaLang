package jena.lang.source;

public final class WrongSourceLocation implements SourceLocation
{
    @Override
    public void location(SourceLocationAction action)
    {
        action.call(-1, -1);
    }
}