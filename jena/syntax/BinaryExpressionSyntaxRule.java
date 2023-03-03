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
            for(char operator : new char[] { '+', '-', '*', '/' }) new OperatorSyntaxRule(operator).match(leftSpan, (plus, plusSpan) ->
            {
                literal.match(plusSpan, (right, rightSpan) ->
                {
                    action.call(new BinaryExpressionSyntax(left, plus, right), rightSpan);
                });
            });
        });
    }
}