package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public final class InvocationExpressionSyntaxRule implements ContinuousSyntaxRule
{
    @Override
    public void match(SourceSpan span, Syntax last, ContinuousSyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        new AnyExpressionSyntaxRule().match(span, (arg, argSpan) ->
        {
            action.call(new InvocationExpressionSyntax(last, arg), argSpan, new AnyContinuousSyntaxRule());
        }, mistakeAction);
    }
}