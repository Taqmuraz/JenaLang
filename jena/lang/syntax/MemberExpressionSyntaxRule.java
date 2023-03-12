package jena.lang.syntax;

import jena.lang.source.SingleCharacterSource;
import jena.lang.source.SourceSpan;

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
                    action.call(new MemberExpressionSyntax(new SyntaxSource(name), expression), expressionSpan);
                }, mistakeAction);
            }
            else
            {
                mistakeAction.call(new WrongSourceMistake(nameSpan.at(0), new SingleCharacterSource(':')), nameSpan);
            }
        }, mistakeAction);
    }
}