package jena.lang.source;

public final class FixedSourceLocation implements SourceLocation
{
    private int line;
    private int symbol;
    
    public FixedSourceLocation(int line, int symbol)
    {
        this.line = line;
        this.symbol = symbol;
    }

    @Override
    public void location(SourceLocationAction action)
    {
        action.call(line, symbol);
    }
}