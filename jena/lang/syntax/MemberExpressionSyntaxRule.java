package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.SyntaxText;

public final class MemberExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        new NameExpressionSyntaxRule().match(span, (name, nameSpan) ->
        {
            if(nameSpan.at(0).text().compareString(":"))
            {
                new AnyExpressionSyntaxRule().match(nameSpan.skip(1), (expression, expressionSpan) ->
                {
                    action.call(new MemberExpressionSyntax(new SyntaxText(name), expression), expressionSpan);
                }, mistakeAction);
            }
            else
            {
                mistakeAction.call(new WrongSourceMistake(nameSpan.at(0), new SingleCharacterText(':')), nameSpan);
            }
        }, mistakeAction);
    }
}