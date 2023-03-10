package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.source.StringSource;

public class BinaryExpressionSyntaxRule implements ContinuousSyntaxRule
{
    @Override
    public void match(SourceSpan span, Syntax last, SyntaxSpanAction action)
    {
        for(String operatorSymbol : new String[] { "+", "-", "*", "/", "<", ">", "==", "!=" }) new OperatorSyntaxRule(new StringSource(operatorSymbol)).match(span, (operator, operatorSpan) ->
        {
            if(operator instanceof BinaryOperatorSyntax)
            {
                new AnyExpressionSyntaxRule().match(operatorSpan, (right, rightSpan) ->
                {                
                    Syntax result = new BinaryExpressionSyntax(last, (BinaryOperatorSyntax)operator, right);
                    action.call(result, rightSpan);
                    this.match(rightSpan, result, action);
                });
            }
        });
    }
}