package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public final class NameExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action)
    {
        if(span.at(0).isKind(Character::isAlphabetic))
        {
            action.call(new NameExpressionSyntax(span.at(0)), span.skip(1));
        }
    }
}