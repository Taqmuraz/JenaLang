package jena.lang;

import jena.lang.source.EmptySourceFilter;
import jena.lang.source.FileSource;
import jena.lang.source.SingleCharacterKind;
import jena.lang.source.SourceFlow;
import jena.lang.source.SpaceCharacterKind;
import jena.lang.source.StringLiteralFlow;
import jena.syntax.JenaSyntaxReader;

public class Program
{
    public static void main(String[] args)
    {
        try
        {
            SourceFlow flow = new StringLiteralFlow(new FileSource("source.jena"))
                .split(new SingleCharacterKind('('))
                .split(new SingleCharacterKind(')'))
                .split(new SingleCharacterKind('{'))
                .split(new SingleCharacterKind('}'))
                .split(new SingleCharacterKind('['))
                .split(new SingleCharacterKind(']'))
                .split(new SingleCharacterKind('+'))
                .split(new SingleCharacterKind('-'))
                .split(new SingleCharacterKind('*'))
                .split(new SingleCharacterKind('/'))
                .notFilter(new EmptySourceFilter())
                .notKindFilter(SpaceCharacterKind.instance);

            flow.read(source ->
            {
                System.out.print("source : ");
                source.read(StartPosition.instance, MaxCount.instance, (c, n) -> System.out.print(c));
                System.out.println();
            });

            new JenaSyntaxReader().read(flow.span(), syntax ->
            {
                syntax.source(source -> System.out.print(source.text()));
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