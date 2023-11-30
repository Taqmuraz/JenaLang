package jena.lang.syntax;

import jena.lang.source.Source;
import jena.lang.source.SourceLocation;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public interface SyntaxMistake
{
    void print(TextWriter printer);

    static SyntaxMistake of(Text text)
    {
        return printer -> printer.write(text);
    }

    default SyntaxMistake withSource(Source source)
    {
        return printer ->
        {
            printer.write("Source: `");
            printer.write(source.text());
            printer.write("` ");
            print(printer);
        };
    }

    default SyntaxMistake located(SourceLocation location)
    {
        return new LocatedSyntaxMistake(this, location);
    }

    default RuntimeException exception(String message)
    {
        StringBuilder sb = new StringBuilder();
        print(t -> sb.append(t.string()));
        return new RuntimeException(String.format("%s, %s", message, sb.toString()));
    }
}