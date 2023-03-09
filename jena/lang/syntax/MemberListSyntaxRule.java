package jena.lang.syntax;

import jena.lang.source.SingleCharacterSource;
import jena.lang.source.Source;
import jena.lang.source.SourceSpan;
import jena.lang.syntax.ExpressionListSyntaxRule.ArgumentListSpanAction;

public final class MemberListSyntaxRule
{
    public void match(SourceSpan span, ArgumentListSpanAction action)
    {
        Source openBrace = new SingleCharacterSource('(');
        Source closeBrace = new SingleCharacterSource(')');
        new ExpressionListSyntaxRule(new MemberExpressionSyntaxRule(), openBrace, closeBrace).match(span, (parameters, endSpan) ->
        {
            action.call(parameters, endSpan);
        });
    }
}