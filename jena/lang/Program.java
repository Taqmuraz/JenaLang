package jena.lang;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import jena.lang.source.InputStreamLineSource;
import jena.lang.value.Value;

public class Program
{
    public static void main(String[] args)
    {
        try
        {
            while(true)
            {
                Value.of(new InputStreamLineSource(System.in));
            }
        }
        catch(Throwable error)
        {
            try
            {
                var writer = new PrintStream(new FileOutputStream(new File("log.txt")));

                while(error != null)
                {
                    error.printStackTrace(writer);
                    error.printStackTrace(System.out);
                    error = error.getCause();
                }
            }
            catch(Throwable th)
            {

            }
        }
    }
}