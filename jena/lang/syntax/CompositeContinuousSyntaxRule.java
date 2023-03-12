package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public final class CompositeContinuousSyntaxRule implements ContinuousSyntaxRule
{
    private ContinuousSyntaxRule[] rules;

    public CompositeContinuousSyntaxRule(ContinuousSyntaxRule... rules)
    {
        this.rules = rules;
    }

    @Override
    public void match(SourceSpan span, Syntax last, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        for(ContinuousSyntaxRule rule : rules) rule.match(span, last, action, mistakeAction);
    }
}