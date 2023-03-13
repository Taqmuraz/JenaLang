package jena.lang.syntax;

import jena.lang.GenericAction;
import jena.lang.GenericBuffer;
import jena.lang.GenericPair;
import jena.lang.SingleGenericBuffer;
import jena.lang.StructPair;
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
        GenericAction<GenericPair<SourceSpan,GenericBuffer<Syntax>>> matchAfterName = pair -> pair.both((argSpan, args) ->
        {
            if(argSpan.at(0).text().compareString("->"))
            {
                expressionRule.match(argSpan.skip(1), (expression, expressionSpan) ->
                {
                    action.call(args, expression, expressionSpan);
                }, mistakeAction);
            }
            else
            {
                mistakeAction.call(new WrongSourceMistake(argSpan.at(0), new StringText("->")), span);
            }
        });

        argumentRule.match(span, (arg, argSpan) ->
        {
            matchAfterName.call(new StructPair<>(argSpan, new SingleGenericBuffer<>(arg)));
        }, mistakeAction);
        new ParenthesizedListSyntaxRule(argumentRule).match(span, (args, argSpan) ->
        {
            matchAfterName.call(new StructPair<>(argSpan, args));
        }, mistakeAction);
    }
}