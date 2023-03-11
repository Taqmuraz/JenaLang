package jena.lang.syntax;

import jena.lang.SingleGenericBuffer;
import jena.lang.source.SourceSpan;

public final class SingleListSyntaxRule implements SyntaxListRule
{
    private SyntaxRule elementRule;

    public SingleListSyntaxRule(SyntaxRule elementRule)
    {
        this.elementRule = elementRule;
    }

    @Override
    public void match(SourceSpan span, SyntaxListSpanAction action)
    {
        elementRule.match(span, (element, elementSpan) ->
        {
            action.call(new SingleGenericBuffer<Syntax>(element), elementSpan);
        });
    }
}