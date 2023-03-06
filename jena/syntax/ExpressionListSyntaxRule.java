package jena.syntax;

import java.util.ArrayList;
import java.util.List;

import jena.lang.source.SingleCharacterKind;
import jena.lang.source.SourceSpan;

public final class ExpressionListSyntaxRule
{
    public interface ArgumentListSpanAction
    {
        void call(Syntax[] arguments, SourceSpan span);
    }

    private static class ExpressionListMatchAction implements SyntaxSpanAction
    {
        List<Syntax> expressions = new ArrayList<Syntax>();
        SyntaxRule anyExpression = new AnyExpressionSyntaxRule();
        ArgumentListSpanAction action;
        
        public ExpressionListMatchAction(ArgumentListSpanAction action)
        {
            this.action = action;
        }

        @Override
        public void call(Syntax expression, SourceSpan expressionSpan)
        {
            expressions.add(expression);
            if(expressionSpan.at(0).isKind(new SingleCharacterKind(')')))
            {
                action.call(expressions.toArray(Syntax[]::new), expressionSpan.skip(1));
            }
            else
            {
                anyExpression.match(expressionSpan, this);
            }
        }
    }

    public void match(SourceSpan span, ArgumentListSpanAction action)
    {
        if(span.at(0).isKind(new SingleCharacterKind('(')))
        {
            new AnyExpressionSyntaxRule().match(span.skip(1), new ExpressionListMatchAction(action));
        }
    }
}