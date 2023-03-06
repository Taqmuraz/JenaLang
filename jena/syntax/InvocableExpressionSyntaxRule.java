package jena.syntax;

import jena.lang.source.SourceSpan;

public final class InvocableExpressionSyntaxRule implements SyntaxRule
{
    private static SyntaxRule rule = new CompositeSyntaxRule(
        new BasicExpressionSyntaxRule(),
        new MemberAccessExpressionSyntaxRule()
    );

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action)
    {
        rule.match(span, action);
    }
}