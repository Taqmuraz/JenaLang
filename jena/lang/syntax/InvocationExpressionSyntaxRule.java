package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public final class InvocationExpressionSyntaxRule implements ContinuousSyntaxRule
{
    @Override
    public void match(SourceSpan span, Syntax last, SyntaxSpanAction action)
    {
        new ArgumentListSyntaxRule().match(span, (args, argsSpan) ->
        {
            action.call(new InvocationExpressionSyntax(last, args), argsSpan);
        });
    }
}