package jena.lang.value;

import jena.lang.Count;
import jena.lang.Position;
import jena.lang.source.Source;
import jena.lang.source.SourceLocation;
import jena.lang.source.SourceSymbolAction;
import jena.lang.source.StringSource;

public final class ValueSource implements Source
{
    private Source source;

    public ValueSource(Value value)
    {
        StringBuilder sb = new StringBuilder();
        value.print(s -> sb.append(s.text()));
        source = new StringSource(sb.toString());
    }

    @Override
    public void read(Position position, Count count, SourceSymbolAction buffer)
    {
        source.read(position, count, buffer);
    }

    @Override
    public SourceLocation location()
    {
        return source.location();
    }
}