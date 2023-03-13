package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.text.StringText;

public class BinaryExpressionSyntaxRule implements ContinuousSyntaxRule
{
    @Override
    public void match(SourceSpan span, Syntax last, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        for(String operatorSymbol : new String[] { "+", "-", "*", "/", "<", ">", "==", "!=" }) new OperatorSyntaxRule(new StringText(operatorSymbol)).match(span, (operator, operatorSpan) ->
        {
            if(operator instanceof BinaryOperatorSyntax)
            {
                new AnyExpressionSyntaxRule().match(operatorSpan, (right, rightSpan) ->
                {                
                    Syntax result = new BinaryExpressionSyntax(last, (BinaryOperatorSyntax)operator, right);
                    action.call(result, rightSpan);
                    this.match(rightSpan, result, action, mistakeAction);
                }, mistakeAction);
            }
            else
            {
                mistakeAction.call(new KindSourceMistake(span.at(0), new StringText("binary operator")), operatorSpan);
            }
        }, mistakeAction);
    }
}