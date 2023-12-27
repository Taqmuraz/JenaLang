package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.text.TextWriter;
import jena.lang.value.Namespace;
import jena.lang.value.ValueFunction;

public class SafeSyntax implements Syntax
{
    Syntax source;
    SourceSpan start;
    SourceSpan end;

    public SafeSyntax(Syntax source, SourceSpan start, SourceSpan end)
    {
        this.source = source;
        this.start = start;
        this.end = end;
    }

    static final String redOutput = "\u001B[31m";
    static final String defaultOutput = "\u001B[0m";
    static final String yellowOutput = "\u001B[33m";

    JenaException exception(Throwable cause)
    {
        var sourceText = new StringBuilder();
        var c = start;
        while(c.code() != end.code())
        {
            sourceText.append(c.at(0).text().string());
            sourceText.append(" ");
            c = c.skip(1);
        }

        return new JenaException(String.format(
            "Caused by jena source : %s%s%s%s%s",
            redOutput,
            sourceText.toString(),
            yellowOutput,
            start.at(0).location().string(),
            defaultOutput).replace("\n", "\n\t"), cause);
    }

    @Override
    public void text(TextWriter writer)
    {
        try
        {
            source.text(writer);
        }
        catch(JenaException j)
        {
            throw j;
        }
        catch(Throwable th)
        {
            throw exception(th);
        }
    }

    @Override
    public ValueFunction value(Namespace namespace)
    {
        try
        {
            return source.value(namespace);
        }
        catch(JenaException j)
        {
            throw j;
        }
        catch(Throwable th)
        {
            throw exception(th);
        }
    }
}