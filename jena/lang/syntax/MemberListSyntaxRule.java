package jena.lang.syntax;

import jena.lang.source.SingleCharacterSource;
import jena.lang.source.Source;
import jena.lang.source.SourceSpan;

public final class MemberListSyntaxRule implements SyntaxListRule
{
    @Override
    public void match(SourceSpan span, SyntaxListSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        Source openBrace = new SingleCharacterSource('(');
        Source closeBrace = new SingleCharacterSource(')');
        new ExpressionListSyntaxRule(new MemberExpressionSyntaxRule(), openBrace, closeBrace).match(span, (parameters, endSpan) ->
        {
            action.call(parameters, endSpan);
        }, mistakeAction);
    }
}