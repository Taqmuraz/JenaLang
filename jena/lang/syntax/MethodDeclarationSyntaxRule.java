package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public final class MethodDeclarationSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action)
    {
        if(span.at(0).text().compareString("method"))
        {
            new ParameterListSyntaxRule().match(span.skip(1), (arguments, argumentsSpan) ->
            {
                new AnyExpressionSyntaxRule().match(argumentsSpan, (expression, expressionSpan) ->
                {
                    action.call(new MethodDeclarationSyntax(arguments, expression), expressionSpan);
                });
            });
        }
    }
}