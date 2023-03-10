package jena.lang.source;

import java.io.File;
import java.util.Scanner;

import jena.lang.Count;
import jena.lang.Position;

public class FileSource implements Source
{
    private Source source;

    public FileSource(File file)
    {
        StringBuilder text = new StringBuilder();
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(file);
            while(scanner.hasNextLine())
            {
                text.append(scanner.nextLine());
                text.append('\n');
            }
        }
        catch(Throwable error)
        {
            if(scanner != null) scanner.close();
        }
        source = new StringSource(text.toString());
    }

    @Override
    public void read(Position position, Count count, SourceSymbolAction buffer)
    {
        source.read(position, count, buffer);
    }
}