package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public final class ArrowMethodSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        new ArrowExpressionSyntaxRule(
            new CompositeSyntaxRule(
                new NameExpressionSyntaxRule(),
                new NoneExpressionSyntaxRule()),
            new AnyExpressionSyntaxRule()
        ).match(span, (arguments, expression, endSpan) ->
        {
            action.call(new ArrowExpressionSyntax(arguments, expression), endSpan);
        }, mistakeAction);
    }
}