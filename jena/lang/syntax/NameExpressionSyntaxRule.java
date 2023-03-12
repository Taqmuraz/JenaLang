package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.source.StringSource;

public final class NameExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        if(span.at(0).isKind(Character::isAlphabetic))
        {
            action.call(new NameExpressionSyntax(span.at(0)), span.skip(1));
        }
        else
        {
            mistakeAction.call(new KindSourceMistake(span.at(0), new StringSource("alphabetic")), span);
        }
    }
}