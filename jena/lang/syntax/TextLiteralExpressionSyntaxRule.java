package jena.lang.syntax;

import jena.lang.source.SingleCharacterSource;
import jena.lang.source.Source;
import jena.lang.source.SourceSpan;

public final class TextLiteralExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        Source symbol = new SingleCharacterSource('\"');
        if(span.at(0).text().compare(symbol.text()))
        {
            if(span.at(2).text().compare(symbol.text()))
            {
                action.call(new TextLiteralExpressionSyntax(span.at(1)), span.skip(3));
            }
            else
            {
                mistakeAction.call(new WrongSourceMistake(span.at(2), symbol), span.skip(2));
            }
        }
        else
        {
            mistakeAction.call(new WrongSourceMistake(span.at(0), symbol), span);
        }
    }
}