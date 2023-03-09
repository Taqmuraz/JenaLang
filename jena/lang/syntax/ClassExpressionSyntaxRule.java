package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public final class ClassExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action)
    {
        if(span.at(0).text().compareString("class"))
        {
            new ArgumentListSyntaxRule().match(span.skip(1), (arguments, argSpan) ->
            {
                new MemberListSyntaxRule().match(argSpan, (members, memberSpan) ->
                {
                    action.call(new ClassExpressionSyntax(arguments, members), memberSpan);
                });
            });
        }
    }
}