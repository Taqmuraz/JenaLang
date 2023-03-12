package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public final class ContinuedSyntaxRule implements SyntaxRule
{
    private SyntaxRule rule;
    private ContinuousSyntaxRule continuous;

    public ContinuedSyntaxRule(SyntaxRule rule, ContinuousSyntaxRule continuous)
    {
        this.rule = rule;
        this.continuous = continuous;
    }

    void continued(Syntax lastSyntax, SourceSpan lastSpan, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        action.call(lastSyntax, lastSpan);
        continuous.match(lastSpan, lastSyntax, (l, s) -> continued(l, s, action, mistakeAction), mistakeAction);
    }

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        rule.match(span, (lastSyntax, lastSpan) ->
        {
            continued(lastSyntax, lastSpan, action, mistakeAction);
        }, mistakeAction);
    }
}