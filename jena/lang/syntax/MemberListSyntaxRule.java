package jena.lang.syntax;

import jena.lang.text.SingleCharacterText;
import jena.lang.text.Text;
import jena.lang.source.SourceSpan;

public final class MemberListSyntaxRule implements SyntaxListRule
{
    @Override
    public void match(SourceSpan span, SyntaxListSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        Text openBrace = new SingleCharacterText('(');
        Text closeBrace = new SingleCharacterText(')');
        new ExpressionListSyntaxRule(new BindingExpressionSyntaxRule(), openBrace, closeBrace).match(span, (parameters, endSpan) ->
        {
            action.call(parameters, endSpan);
        }, mistakeAction);
    }
}