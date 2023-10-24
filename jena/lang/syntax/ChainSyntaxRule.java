package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.text.SingleCharacterText;

public final class ChainSyntaxRule implements SyntaxRule
{
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        new ParenthesizedListSyntaxRule(new AnyExpressionSyntaxRule()).match(span, (expressions, expressionsSpan) ->
        {
            action.call(new ExpressionListSyntax(
                expressions,
                new SingleCharacterText('('),
                new SingleCharacterText(')'),
                b -> b.at(b.length() - 1)), expressionsSpan);
        }, mistakeAction);
    }
}