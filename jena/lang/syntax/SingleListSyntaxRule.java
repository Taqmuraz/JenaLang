package jena.lang.syntax;

import jena.lang.SingleBuffer;
import jena.lang.source.SourceSpan;

public final class SingleListSyntaxRule implements SyntaxListRule
{
    private SyntaxRule elementRule;

    public SingleListSyntaxRule(SyntaxRule elementRule)
    {
        this.elementRule = elementRule;
    }

    @Override
    public void match(SourceSpan span, SyntaxListSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        elementRule.match(span, (element, elementSpan) ->
        {
            action.call(new SingleBuffer<Syntax>(element), elementSpan);
        }, mistakeAction);
    }
}