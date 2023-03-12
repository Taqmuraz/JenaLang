package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public final class CompositeSyntaxListRule implements SyntaxListRule
{
    private SyntaxListRule[] rules;

    public CompositeSyntaxListRule(SyntaxListRule... rules)
    {
        this.rules = rules;
    }

    @Override
    public void match(SourceSpan span, SyntaxListSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        for(SyntaxListRule rule : rules) rule.match(span, action, mistakeAction);
    }
}