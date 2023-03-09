package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.source.StringSource;

public class BinaryExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action)
    {
        SyntaxRule expression = new AnyExpressionSyntaxRule().except(getClass());
        expression.match(span, (left, leftSpan) ->
        {
            for(String operatorSymbol : new String[] { "+", "-", "*", "/", "<", ">", "=", "!" }) new OperatorSyntaxRule(new StringSource(operatorSymbol)).match(leftSpan, (operator, operatorSpan) ->
            {
                SyntaxSpanAction rightAction = (right, rightSpan) ->
                {
                    if(operator instanceof BinaryOperatorSyntax) action.call(new BinaryExpressionSyntax(left, (BinaryOperatorSyntax)operator, right), rightSpan);
                };
                expression.match(operatorSpan, rightAction);
                new SyntaxGuess(operatorSpan, this).guess(rightAction);
            });
        });
    }
}