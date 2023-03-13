package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.text.StringText;

public final class NameExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        if(span.at(0).isKind(Character::isAlphabetic))
        {
            action.call(new NameExpressionSyntax(span.at(0).text()), span.skip(1));
        }
        else
        {
            mistakeAction.call(new KindSourceMistake(span.at(0), new StringText("alphabetic")), span);
        }
    }
}