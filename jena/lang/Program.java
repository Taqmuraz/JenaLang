package jena.lang;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program
{
    public static void main(String[] args)
    {
        try
        {
            Scanner scanner;
            scanner = new Scanner(new File("source.jena"));
            List<String> lines = new ArrayList<String>();

            while(scanner.hasNextLine())
            {
                lines.add(scanner.nextLine());
            }

            SourceFlow flow = new StringLiteralFlow(String.join("\n", lines));
            flow.read(MaxCount.instance, source ->
            {
                System.out.print("source : ");
                source.read(System.out::print);
                System.out.println();
            });
        }
        catch(Throwable error)
        {
            while(error != null)
            {
                error.printStackTrace(System.out);
                error = error.getCause();
            }
        }
    }
}