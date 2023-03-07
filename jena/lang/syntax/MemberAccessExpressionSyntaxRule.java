package jena.lang.syntax;

import jena.lang.source.SingleCharacterKind;
import jena.lang.source.SourceSpan;

public final class MemberAccessExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action)
    {
        new BasicExpressionSyntaxRule().match(span, (expression, expressionSpan) ->
        {
            if(expressionSpan.at(0).isKind(new SingleCharacterKind('.')))
            {
                action.call(new MemberAccessExpressionSyntax(expression, expressionSpan.at(1)), expressionSpan.skip(2));
            }
        });
    }
}