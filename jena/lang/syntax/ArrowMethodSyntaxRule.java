package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public final class ArrowMethodSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action)
    {
        new ArrowExpressionSyntaxRule(
            new NameExpressionSyntaxRule(),
            new AnyExpressionSyntaxRule()
        ).match(span, (arguments, expression, endSpan) ->
        {
            action.call(new MethodExpressionSyntax(arguments, expression), endSpan);
        });
    }
}