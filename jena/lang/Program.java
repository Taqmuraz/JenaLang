package jena.lang;

public class Program
{
    public static void main(String[] args)
    {
        try
        {
            SourceFlow flow = new EmptySourceFilterFlow(new StringLiteralFlow(new FileSource("source.jena")));
            flow.read(MaxCount.instance, source ->
            {
                System.out.print("source : ");
                source.read(StartPosition.instance, MaxCount.instance, (c, n) -> System.out.print(c));
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