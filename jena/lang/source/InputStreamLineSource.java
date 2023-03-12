package jena.lang.source;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import jena.lang.Count;
import jena.lang.Position;

public final class InputStreamLineSource implements Source
{
    private Source source;

    public InputStreamLineSource(InputStream stream)
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        try
        {
            source = new StringSource(reader.readLine());
        }
        catch(Exception ex)
        {
            System.out.println(ex);
            source = new EmptySource();
        }
    }

    @Override
    public void read(Position position, Count count, SourceSymbolAction buffer)
    {
        source.read(position, count, buffer);
    }

    @Override
    public SourceLocation location(int position)
    {
        return source.location(position);
    }
}