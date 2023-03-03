package jena.syntax;

import jena.lang.source.SourceSpan;

public class BinaryExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action)
    {
        SyntaxRule expression = new AnyExpressionSyntaxRule().except(getClass());
        expression.match(span, (left, leftSpan) ->
        {
            for(char operatorSymbol : new char[] { '+', '-', '*', '/' }) new OperatorSyntaxRule(operatorSymbol).match(leftSpan, (operator, operatorSpan) ->
            {
                SyntaxSpanAction rightAction = (right, rightSpan) ->
                {
                    action.call(new BinaryExpressionSyntax(left, operator, right), rightSpan);
                };
                expression.match(operatorSpan, rightAction);
                new SyntaxGuess(operatorSpan, this).guess(rightAction);
            });
        });
    }
}