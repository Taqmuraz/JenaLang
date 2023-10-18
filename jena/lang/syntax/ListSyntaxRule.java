package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public final class ListSyntaxRule implements SyntaxListRule
{
    public void match(SourceSpan span, SyntaxListSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        new ParenthesizedListSyntaxRule(new AnyExpressionSyntaxRule()).match(span, action, mistakeAction);
    }
}