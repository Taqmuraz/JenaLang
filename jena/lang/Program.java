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
            SourceFlow flow = new StringLiteralFlow(new FileSource("source.jena"));
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