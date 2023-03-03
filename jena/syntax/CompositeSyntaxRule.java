package jena.syntax;

import jena.lang.source.SourceSpan;

public class CompositeSyntaxRule implements SyntaxRule
{
    private SyntaxRule[] rules;

    public CompositeSyntaxRule(SyntaxRule... rules)
    {
        this.rules = rules;
    }

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action)
    {
        for(SyntaxRule rule : rules) rule.match(span, action);
    }
}