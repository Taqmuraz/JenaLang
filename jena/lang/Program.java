package jena.lang;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import jena.lang.source.InputStreamLineSource;
import jena.lang.source.Source;
import jena.lang.syntax.JenaSyntaxReader;
import jena.lang.syntax.TextSyntaxMistakePrinter;
import jena.lang.value.IONamespace;
import jena.lang.value.JavaNamespace;
import jena.lang.value.Namespace;
import jena.lang.value.StorageNamespace;
import jena.lang.value.SwingNamespace;

public class Program
{
    public static void main(String[] args)
    {
        try
        {
            Namespace namespace = new StorageNamespace(new File("sources"),
                new IONamespace().nested(
                    new SwingNamespace().nested(
                        JavaNamespace.create())));
            while(true)
            {
                Source source = new InputStreamLineSource(System.in);

                /*new JenaSourceFlow(source).read(s ->
                {
                    System.out.print("source : ");
                    s.read(StartPosition.instance, MaxCount.instance, (c, n) -> System.out.print(c));
                    System.out.println();
                });*/

                new JenaSyntaxReader(source).read(syntax ->
                {
                    //syntax = syntax.decomposed();
                    //syntax.text(s -> System.out.print(s.string()));
                    //System.out.println();
                    
                    syntax.value(namespace);
                    System.out.println();
                }, new TextSyntaxMistakePrinter());
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