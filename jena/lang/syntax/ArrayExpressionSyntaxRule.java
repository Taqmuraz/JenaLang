package jena.lang.syntax;

import jena.lang.source.SingleCharacterSource;
import jena.lang.source.Source;
import jena.lang.source.SourceSpan;

public final class ArrayExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        Source openBrace = new SingleCharacterSource('[');
        Source closeBrace = new SingleCharacterSource(']');
        new ExpressionListSyntaxRule(new AnyExpressionSyntaxRule(), openBrace, closeBrace).match(span, (elements, endSpan) ->
        {
            action.call(new ExpressionListSyntax(elements, openBrace, closeBrace), endSpan);
        }, mistakeAction);
    }
}