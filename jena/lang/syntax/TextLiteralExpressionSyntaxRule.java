package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public final class TextLiteralExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action)
    {
        if(span.at(0).text().compareString("\"") && span.at(2).text().compareString("\""))
        {
            action.call(new TextLiteralExpressionSyntax(span.at(1)), span.skip(3));
        }
    }   
}