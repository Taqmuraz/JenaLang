package jena.lang.syntax;

import jena.lang.GenericAction;
import jena.lang.GenericBuffer;
import jena.lang.GenericPair;
import jena.lang.SingleGenericBuffer;
import jena.lang.StructPair;
import jena.lang.source.SourceSpan;

public final class ArrowExpressionSyntaxRule
{
    private SyntaxRule argumentRule;
    private SyntaxRule expressionRule;


    public ArrowExpressionSyntaxRule(SyntaxRule argumentRule, SyntaxRule expressionRule)
    {
        this.argumentRule = argumentRule;
        this.expressionRule = expressionRule;
    }

    public void match(SourceSpan span, ArrowSpanAction action)
    {
        GenericAction<GenericPair<SourceSpan,GenericBuffer<Syntax>>> matchAfterName = pair -> pair.both((argSpan, args) ->
        {
            if(argSpan.at(0).text().compareString("->"))
            {
                expressionRule.match(argSpan.skip(1), (expression, expressionSpan) ->
                {
                    action.call(args, expression, expressionSpan);
                });
            }
        });

        argumentRule.match(span, (arg, argSpan) ->
        {
            matchAfterName.call(new StructPair<>(argSpan, new SingleGenericBuffer<>(arg)));
        });
        new ParenthesizedListSyntaxRule(argumentRule).match(span, (args, argSpan) ->
        {
            matchAfterName.call(new StructPair<>(argSpan, args));
        });
    }
}