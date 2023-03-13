package jena.lang.syntax;

import jena.lang.GenericBuffer;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public final class ExpressionListWriter
{
    private Text openBrace;
    private Text closeBrace;

    public ExpressionListWriter(Text openBrace, Text closeBrace)
    {
        this.openBrace = openBrace;
        this.closeBrace = closeBrace;
    }

    public void write(GenericBuffer<Syntax> list, TextWriter writer)
    {
        writer.write(openBrace);
        list.flow().join(arg -> arg.text(writer), () -> writer.write(new SingleCharacterText(',')));
        writer.write(closeBrace);
    }
}