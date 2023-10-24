package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.text.StringText;

public final class ArrowExpressionSyntaxRule
{
    private SyntaxRule argumentRule;
    private SyntaxRule expressionRule;


    public ArrowExpressionSyntaxRule(SyntaxRule argumentRule, SyntaxRule expressionRule)
    {
        this.argumentRule = argumentRule;
        this.expressionRule = expressionRule;
    }

    public void match(SourceSpan span, ArrowSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        argumentRule.match(span, (arg, argSpan) ->
        {
            if(argSpan.at(0).text().compareString("->"))
            {
                expressionRule.match(argSpan.skip(1), (expression, expressionSpan) ->
                {
                    action.call(arg, expression, expressionSpan);
                }, mistakeAction);
            }
            else
            {
                mistakeAction.call(new WrongSourceMistake(argSpan.at(0), new StringText("->")), span);
            }
        }, mistakeAction);
    }
}