package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public final class ContinuedSyntaxRule implements SyntaxRule
{
    private SyntaxRule rule;
    private ContinuousSyntaxRule continuousRule;

    public ContinuedSyntaxRule(SyntaxRule rule, ContinuousSyntaxRule continuous)
    {
        this.rule = rule;
        this.continuousRule = continuous;
    }

    void continued(Syntax lastSyntax, SourceSpan lastSpan, ContinuousSyntaxRule continuous, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        action.call(lastSyntax, lastSpan);
        continuous.match(lastSpan, lastSyntax, (l, s, c) -> continued(l, s, c, action, mistakeAction), mistakeAction);
    }

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        rule.match(span, (lastSyntax, lastSpan) ->
        {
            continued(lastSyntax, lastSpan, continuousRule, action, mistakeAction);
        }, mistakeAction);
    }
}