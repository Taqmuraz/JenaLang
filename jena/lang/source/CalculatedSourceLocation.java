package jena.lang.source;

import jena.lang.MinCount;
import jena.lang.StartPosition;
import jena.lang.text.Text;

public final class CalculatedSourceLocation implements SourceLocation
{
    private Text origin;
    private Source source;
    private int line;
    private int symbol;

    public CalculatedSourceLocation(Text origin, Source source)
    {
        this.origin = origin;
        this.source = source;
    }

    @Override
    public void location(int position, SourceLocationAction action)
    {
        line = 1;
        symbol = 1;
        source.read(StartPosition.instance, new MinCount(position), (c, n) ->
        {
            switch(c)
            {
                case '\n': line++; symbol = 1; break;
                default: symbol++;
            }
        });
        action.call(origin, line, symbol);
    }
}