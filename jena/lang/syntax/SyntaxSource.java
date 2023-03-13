package jena.lang.syntax;

import jena.lang.Count;
import jena.lang.Position;
import jena.lang.source.Source;
import jena.lang.source.SourceLocation;
import jena.lang.source.SourceSymbolAction;
import jena.lang.source.StringSource;

public final class SyntaxSource implements Source
{
    private Source source;

    public SyntaxSource(Syntax syntax)
    {
        StringBuilder sb = new StringBuilder();
        syntax.source(s -> sb.append(s.text()));
        source = new StringSource(sb.toString());
    }

    @Override
    public void read(Position position, Count count, SourceSymbolAction buffer)
    {
        if(source != null) source.read(position, count, buffer);
    }

    @Override
    public SourceLocation location()
    {
        return source.location();
    }
}