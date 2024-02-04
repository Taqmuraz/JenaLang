package jena.lang;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import jena.lang.source.InputStreamLineSource;
import jena.lang.source.Source;
import jena.lang.text.Text;
import jena.lang.value.TextValue;
import jena.lang.value.Value;

public class Program
{
    public static void main(String[] args)
    {
        try
        {
            if(args.length == 0)
            {
                while(true)
                {
                    Value.of(new InputStreamLineSource(System.in));
                }
            }
            else
            {
                var source = Value.of(Source.of(new File(args[0])));
                for(int i = 1; i < args.length; i++)
                {
                    source = source.call(new TextValue(Text.of(args[i])));
                }
            }
        }
        catch(Throwable error)
        {
            try(var writer = new PrintStream(new FileOutputStream(new File("log.txt"))))
            {
                System.out.println(error);
                writer.println(error);
            }
            catch(Throwable th)
            {
                System.out.println(th);
            }
        }
    }
}