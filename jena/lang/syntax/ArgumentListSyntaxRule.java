package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public final class ArgumentListSyntaxRule implements SyntaxListRule
{
    public void match(SourceSpan span, SyntaxListSpanAction action)
    {
        new ParenthesizedListSyntaxRule(new AnyExpressionSyntaxRule()).match(span, action);
    }
}