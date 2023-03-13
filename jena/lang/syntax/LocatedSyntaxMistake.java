package jena.lang.syntax;

import jena.lang.source.SourceLocation;
import jena.lang.text.TextWriter;

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
    public void print(TextWriter writer)
    {
        mistake.print(writer);
        new LocationPrinter(location).print(writer);
    }
}