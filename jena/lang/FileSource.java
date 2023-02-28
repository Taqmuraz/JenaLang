package jena.lang;

import java.io.File;
import java.util.Scanner;

public class FileSource implements Source
{
    private String file;

    public FileSource(String file)
    {
        this.file = file;
    }

    @Override
    public void read(SourceReader sourceReader)
    {
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(new File(file));
            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                for(int i = 0; i < line.length(); i++) sourceReader.symbol(line.charAt(i));
                sourceReader.symbol('\n');
            }
        }
        catch(Throwable error)
        {
            if(scanner != null) scanner.close();
        }
    }
}