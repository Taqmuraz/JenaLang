package jena.lang.syntax;

import jena.lang.source.SingleCharacterSource;
import jena.lang.source.Source;
import jena.lang.source.SourceSpan;
import jena.lang.syntax.ExpressionListSyntaxRule.ArgumentListSpanAction;

public final class ArgumentListSyntaxRule
{
    public void match(SourceSpan span, ArgumentListSpanAction action)
    {
        Source openBrace = new SingleCharacterSource('(');
        Source closeBrace = new SingleCharacterSource(')');
        new ExpressionListSyntaxRule(new AnyExpressionSyntaxRule(), openBrace, closeBrace).match(span, (arguments, endSpan) ->
        {
            action.call(arguments, endSpan);
        });
    }
}