package jena.lang.source;

import jena.lang.MinCount;
import jena.lang.StartPosition;

public final class CalculatedSourceLocation implements SourceLocation
{
    private int line = 1;
    private int symbol = 1;

    public CalculatedSourceLocation(Source source, int position)
    {
        source.read(StartPosition.instance, new MinCount(position), (c, n) ->
        {
            switch(c)
            {
                case '\n': line++; symbol = 1; break;
                default: symbol++;
            }
        });
    }

    @Override
    public void location(SourceLocationAction action)
    {
        action.call(line, symbol);
    }
}