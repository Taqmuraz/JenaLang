package jena.lang;

import jena.lang.source.FileSource;
import jena.lang.source.JenaSourceFlow;
import jena.lang.source.SourceFlow;
import jena.lang.source.StringLiteralFlow;
import jena.lang.syntax.JenaSyntaxReader;
import jena.lang.value.IONamespace;

public class Program
{
    public static void main(String[] args)
    {
        try
        {
            //while(true)
            {
                SourceFlow flow = new JenaSourceFlow(new StringLiteralFlow(new FileSource("source.jena")));

                /*flow.read(source ->
                {
                    System.out.print("source : ");
                    source.read(StartPosition.instance, MaxCount.instance, (c, n) -> System.out.print(c));
                    System.out.println();
                });*/

                new JenaSyntaxReader().read(flow.span(), syntax ->
                {
                    syntax = syntax.decomposed();
                    //syntax.source(source -> System.out.print(source.text()));
                    //System.out.println();
                    syntax.value(new IONamespace());
                    System.out.println();
                });
            }
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