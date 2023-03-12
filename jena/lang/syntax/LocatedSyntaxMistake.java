package jena.lang.syntax;

import jena.lang.source.SourceAction;
import jena.lang.source.SourceLocation;

public final class LocatedSyntaxMistake implements SyntaxMistake
{
    private SyntaxMistake mistake;
    private SourceLocation location;
    
    public LocatedSyntaxMistake(SyntaxMistake mistake, SourceLocation location)
    {
        this.mistake = mistake;
        this.location = location;
    }

    @Override
    public void print(SourceAction printer)
    {
        mistake.print(printer);
        new LocationPrinter(location).print(printer);
    }
}