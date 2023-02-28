package jena.lang;

import java.io.File;
import java.util.Scanner;

public class FileSource implements Source
{
    private Source source;

    public FileSource(String file)
    {
        StringBuilder text = new StringBuilder();
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(new File(file));
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
    public void read(Position position, Count count, SourceReader buffer)
    {
        source.read(position, count, buffer);
    }
}