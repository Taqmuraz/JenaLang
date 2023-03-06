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

    private class ExpressionListMatchAction implements SyntaxSpanAction
    {
        List<Syntax> expressions = new ArrayList<Syntax>();
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
                rule.match(expressionSpan, this);
            }
        }
    }

    private SyntaxRule rule;

    public ExpressionListSyntaxRule(SyntaxRule rule)
    {
        this.rule = rule;
    }

    public void match(SourceSpan span, ArgumentListSpanAction action)
    {
        if(span.at(0).isKind(new SingleCharacterKind('(')))
        {
            rule.match(span.skip(1), new ExpressionListMatchAction(action));
        }
    }
}