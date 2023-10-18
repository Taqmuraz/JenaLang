package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.text.SingleCharacterText;
import jena.lang.value.TupleValue;

public final class TupleExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        new ListSyntaxRule().match(span, (expressions, expressionsSpan) ->
        {
            action.call(new ExpressionListSyntax(
                expressions,
                new SingleCharacterText('('),
                new SingleCharacterText(')'),
                TupleValue::new), expressionsSpan);
        }, mistakeAction);
    }
}