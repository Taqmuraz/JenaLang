package jena.lang.syntax;

import jena.lang.source.SingleCharacterSource;
import jena.lang.source.Source;
import jena.lang.source.SourceSpan;

public final class ParenthesizedListSyntaxRule implements SyntaxListRule
{
    private SyntaxRule elementRule;

    public ParenthesizedListSyntaxRule(SyntaxRule elementRule)
    {
        this.elementRule = elementRule;
    }

    @Override
    public void match(SourceSpan span, SyntaxListSpanAction action)
    {
        Source openBrace = new SingleCharacterSource('(');
        Source closeBrace = new SingleCharacterSource(')');
        new ExpressionListSyntaxRule(elementRule, openBrace, closeBrace).match(span, action);
    }
}