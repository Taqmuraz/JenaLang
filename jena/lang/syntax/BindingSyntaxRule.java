package jena.lang.syntax;

import java.util.function.Function;

import jena.lang.Action;
import jena.lang.source.SourceSpan;
import jena.lang.text.Text;

public final class BindingSyntaxRule implements SyntaxRule
{
    SyntaxRule left;
    Text symbol;
    Function<Syntax, Syntax> factory;

    public BindingSyntaxRule(SyntaxRule left, Text symbol, Function<Syntax, Syntax> factory)
    {
        this.left = left;
        this.symbol = symbol;
        this.factory = factory;
    }

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, Action mismatch)
    {
        left.match(span, (leftExpr, next) ->
        {
            if(next.at(0).text().compare(symbol))
            {
                action.call(factory.apply(leftExpr), next.skip(1));
            }
            else mismatch.call();
        },
        mismatch);
    }
}