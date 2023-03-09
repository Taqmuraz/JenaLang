package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public final class MemberExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action)
    {
        new NameExpressionSyntaxRule().match(span, (name, nameSpan) ->
        {
            if(nameSpan.at(0).text().compareString(":"))
            {
                new AnyExpressionSyntaxRule().match(nameSpan.skip(1), (expression, expressionSpan) ->
                {
                    action.call(new MemberExpressionSyntax(new SyntaxSource(name), expression), expressionSpan);
                });
            }
        });
    }
}