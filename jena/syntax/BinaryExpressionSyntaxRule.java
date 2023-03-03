package jena.syntax;

import jena.lang.source.SourceSpan;

public class BinaryExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action)
    {
        SyntaxRule literal = new IntegerLiteralSyntaxRule();
        literal.match(span, (left, leftSpan) ->
        {
            for(char operatorSymbol : new char[] { '+', '-', '*', '/' }) new OperatorSyntaxRule(operatorSymbol).match(leftSpan, (operator, operatorSpan) ->
            {
                SyntaxSpanAction rightAction = (right, rightSpan) ->
                {
                    action.call(new BinaryExpressionSyntax(left, operator, right), rightSpan);
                };
                literal.match(operatorSpan, rightAction);
                new SyntaxGuess(operatorSpan, this).guess(rightAction);
            });
        });
    }
}