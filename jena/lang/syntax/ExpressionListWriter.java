package jena.lang.syntax;

import jena.lang.GenericBuffer;
import jena.lang.source.SingleCharacterSource;
import jena.lang.source.Source;

public final class ExpressionListWriter
{
    private Source openBrace;
    private Source closeBrace;

    public ExpressionListWriter(Source openBrace, Source closeBrace)
    {
        this.openBrace = openBrace;
        this.closeBrace = closeBrace;
    }

    public void write(GenericBuffer<Syntax> list, SyntaxSerializer writer)
    {
        writer.source(openBrace);
        list.flow().join(arg -> arg.source(writer), () -> writer.source(new SingleCharacterSource(',')));
        writer.source(closeBrace);
    }
}