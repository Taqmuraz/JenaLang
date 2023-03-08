package jena.lang.syntax;

import jena.lang.source.Source;
import jena.lang.source.SourceSpan;

public final class MethodDeclarationSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action)
    {
        if(span.at(0).text().compareString("method") && span.at(1).isKind(Character::isAlphabetic))
        {
            Source name = span.at(1);
            new ExpressionListSyntaxRule(new NameExpressionSyntaxRule()).matchList(span.skip(2), (arguments, argumentsSpan) ->
            {
                new AnyExpressionSyntaxRule().match(argumentsSpan, (expression, expressionSpan) ->
                {
                    action.call(new MethodDeclarationSyntax(name, arguments, expression), expressionSpan);
                });
            });
        }
    }
}