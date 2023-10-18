package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.text.StringText;

public final class TupleSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        new ListSyntaxRule().match(span, (expressions, expressionsSpan) ->
        {
            action.call(new ExpressionListSyntax(expressions, new StringText("("), new StringText(")")), expressionsSpan);
        }, mistakeAction);
    }
}