package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public final class InvocationExpressionSyntaxRule implements ContinuousSyntaxRule
{
    @Override
    public void match(SourceSpan span, Syntax last, ContinuousSyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        new ArgumentListSyntaxRule().match(span, (args, argsSpan) ->
        {
            action.call(new InvocationExpressionSyntax(last, args), argsSpan, new AnyContinuousSyntaxRule());
        }, mistakeAction);
    }
}