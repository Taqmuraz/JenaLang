package jena.lang.syntax;

import jena.lang.SingleGenericBuffer;
import jena.lang.source.SourceSpan;

public final class ArrowClassSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        new ArrowExpressionSyntaxRule(
            new NameExpressionSyntaxRule(),
            new MemberExpressionSyntaxRule()
        ).match(span, (arguments, expression, endSpan) ->
        {
            action.call(new ClassExpressionSyntax(arguments, new SingleGenericBuffer<Syntax>(expression)), endSpan);
        }, mistakeAction);
    }
}