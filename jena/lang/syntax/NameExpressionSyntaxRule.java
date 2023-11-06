package jena.lang.syntax;

import jena.lang.Action;
import jena.lang.source.SourceSpan;

public final class NameExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, Action mismatch)
    {
        if(span.at(0).isKind(Character::isAlphabetic))
        {
            action.call(new NameExpressionSyntax(span.at(0).text()), span.skip(1));
        }
        else
        {
            mismatch.call();
        }
    }
}