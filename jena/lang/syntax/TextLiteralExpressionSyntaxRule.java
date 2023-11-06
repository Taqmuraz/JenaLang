package jena.lang.syntax;

import jena.lang.Action;
import jena.lang.source.SourceSpan;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.Text;

public final class TextLiteralExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, Action mismatch)
    {
        Text symbol = new SingleCharacterText('\"');
        if(span.at(0).text().compare(symbol) && span.at(2).text().compare(symbol))
        {
            action.call(new TextLiteralExpressionSyntax(span.at(1).text()), span.skip(3));
            return;
        }
        mismatch.call();
    }
}