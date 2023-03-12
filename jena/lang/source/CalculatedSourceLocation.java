package jena.lang.source;

import jena.lang.MinCount;
import jena.lang.StartPosition;

public final class CalculatedSourceLocation implements SourceLocation
{
    private int line;
    private int symbol;

    public CalculatedSourceLocation(Source source, int position)
    {
        source.read(StartPosition.instance, new MinCount(position), (c, n) ->
        {
            switch(c)
            {
                case '\n': line++; symbol = 0; break;
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