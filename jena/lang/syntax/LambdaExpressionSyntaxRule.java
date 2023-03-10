package jena.lang.syntax;

import jena.lang.GenericAction;
import jena.lang.GenericBuffer;
import jena.lang.GenericPair;
import jena.lang.SingleGenericBuffer;
import jena.lang.StructPair;
import jena.lang.source.SourceSpan;

public final class LambdaExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action)
    {
        GenericAction<GenericPair<SourceSpan,GenericBuffer<Syntax>>> matchAfterName = pair -> pair.both((argSpan, args) ->
        {
            if(argSpan.at(0).text().compareString("->"))
            {
                new AnyExpressionSyntaxRule().match(argSpan.skip(1), (expression, expressionSpan) ->
                {
                    action.call(new MethodExpressionSyntax(args, expression), expressionSpan);
                });
            }
        });

        new NameExpressionSyntaxRule().match(span, (arg, argSpan) ->
        {
            matchAfterName.call(new StructPair<>(argSpan, new SingleGenericBuffer<>(arg)));
        });
        new ArgumentListSyntaxRule().match(span, (args, argSpan) ->
        {
            matchAfterName.call(new StructPair<>(argSpan, args));
        });
    }
}