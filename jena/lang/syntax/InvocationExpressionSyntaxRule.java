package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public final class InvocationExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action)
    {
        new InvocableExpressionSyntaxRule().match(span, (left, leftSpan) ->
        {
            new ArgumentListSyntaxRule().match(leftSpan, (args, argsSpan) ->
            {
                action.call(new InvocationExpressionSyntax(left, args), argsSpan);
            });
        });
    }
}