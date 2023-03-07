package jena.lang.syntax;

import jena.lang.Count;
import jena.lang.Position;
import jena.lang.source.Source;
import jena.lang.source.SourceSymbolAction;

public final class SyntaxSource implements Source
{
    private Source source;

    public SyntaxSource(Syntax syntax)
    {
        syntax.source(s -> source = s);
    }

    @Override
    public void read(Position position, Count count, SourceSymbolAction buffer)
    {
        if(source != null) source.read(position, count, buffer);
    }
}