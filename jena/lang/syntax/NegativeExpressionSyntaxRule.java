package jena.lang.syntax;

import jena.lang.EmptyBuffer;
import jena.lang.source.SourceSpan;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.StringText;
import jena.lang.text.Text;

public class NegativeExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        Text minus = new SingleCharacterText('-');
        if(span.at(0).text().compare(minus))
        {
            new AnyExpressionSyntaxRule().match(span.skip(1), (expression, expressionSpan) ->
            {
                action.call(
                    new InvocationExpressionSyntax(
                        new MemberAccessExpressionSyntax(expression,new StringText("negative")),
                        new EmptyBuffer<Syntax>()), expressionSpan);
            }, mistakeAction);
        }
        else
        {
            mistakeAction.call(new WrongSourceMistake(span.at(0), minus), span);
        }
    }
}