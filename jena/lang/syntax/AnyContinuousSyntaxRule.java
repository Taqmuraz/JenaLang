package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public class AnyContinuousSyntaxRule implements ContinuousSyntaxRule
{
    private ContinuousSyntaxRule rule = new InvocationExpressionSyntaxRule();

    @Override
    public void match(SourceSpan span, Syntax last, ContinuousSyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        rule.match(span, last, action, mistakeAction);
    }
}