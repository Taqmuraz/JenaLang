package jena.lang.source;

import java.io.File;
import java.util.Scanner;

import jena.lang.Count;
import jena.lang.Position;
import jena.lang.text.StringText;

public class FileSource implements Source
{
    private Source source;

    public FileSource(File file)
    {
        if(!file.exists())
        {
            throw new RuntimeException("No such file : " + file.getAbsolutePath());
        }
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
        source = new StringSource(new StringText(file.getName()), text.toString());
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