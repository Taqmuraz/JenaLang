package jena.syntax;

import jena.lang.source.SourceSpan;

public class AnyExpressionSyntaxRule implements SyntaxRule
{
    SyntaxRule[] rules =
    {
        new IntegerLiteralSyntaxRule(),
        new ParenthesizedExpressionSyntaxRule(),
        new BinaryExpressionSyntaxRule()
    };

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action)
    {
        for(SyntaxRule rule : rules) rule.match(span, action);
    }

    public SyntaxRule except(Class<? extends SyntaxRule> ruleClass)
    {
        return (span, action) ->
        {
            for(SyntaxRule rule : rules) if (!rule.getClass().equals(ruleClass)) rule.match(span, action);
        };
    }
}