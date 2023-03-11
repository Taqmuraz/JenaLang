package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public final class ClassExpressionSyntaxRule implements SyntaxRule
{
    private SyntaxListRule parameterListRule;
    private SyntaxListRule memberListRule;

    public ClassExpressionSyntaxRule(SyntaxListRule parameterListRule, SyntaxListRule memberListRule)
    {
        this.parameterListRule = parameterListRule;
        this.memberListRule = memberListRule;
    }

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action)
    {
        if(span.at(0).text().compareString("class"))
        {
            parameterListRule.match(span.skip(1), (arguments, argSpan) ->
            {
                memberListRule.match(argSpan, (members, memberSpan) ->
                {
                    action.call(new ClassExpressionSyntax(arguments, members), memberSpan);
                });
            });
        }
    }
}