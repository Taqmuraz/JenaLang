package jena.lang.source;

import java.io.InputStream;
import java.util.Scanner;

import jena.lang.Count;
import jena.lang.Position;

public final class InputStreamLineSource implements Source
{
    private Source source;

    public InputStreamLineSource(InputStream stream)
    {
        Scanner scanner = new Scanner(stream);
        source = new StringSource(scanner.nextLine());
        scanner.close();
    }

    @Override
    public void read(Position position, Count count, SourceSymbolAction buffer)
    {
        source.read(position, count, buffer);
    }
}