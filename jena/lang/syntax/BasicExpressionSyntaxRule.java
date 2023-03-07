package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public class BasicExpressionSyntaxRule implements SyntaxRule
{
    private static final SyntaxRule rule = new CompositeSyntaxRule(
        new IntegerLiteralSyntaxRule(),
        new ParenthesizedExpressionSyntaxRule()
    );

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action)
    {
        rule.match(span, action);
    }
}