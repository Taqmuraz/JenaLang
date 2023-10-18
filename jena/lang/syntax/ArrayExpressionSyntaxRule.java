package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.Text;
import jena.lang.value.TupleValue;

public final class ArrayExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        Text openBrace = new SingleCharacterText('[');
        Text closeBrace = new SingleCharacterText(']');
        new ExpressionListSyntaxRule(new AnyExpressionSyntaxRule(), openBrace, closeBrace).match(span, (elements, endSpan) ->
        {
            action.call(new ExpressionListSyntax(
                elements,
                openBrace,
                closeBrace,
                TupleValue::new), endSpan);
        }, mistakeAction);
    }
}