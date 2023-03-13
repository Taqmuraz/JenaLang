package jena.lang.source;

import jena.lang.text.StringText;

public final class ZeroSourceLocation implements SourceLocation
{
    public ZeroSourceLocation()
    {
    }

    @Override
    public void location(int position, SourceLocationAction action)
    {
        action.call(new StringText("missing source"), 1, 1);
    }
}