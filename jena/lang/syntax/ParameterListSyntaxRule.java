package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public final class ParameterListSyntaxRule implements SyntaxListRule
{
    public void match(SourceSpan span, SyntaxListSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        new ParenthesizedListSyntaxRule(new NameExpressionSyntaxRule()).match(span, action, mistakeAction);
    }
}