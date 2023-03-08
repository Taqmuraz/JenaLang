package jena.lang.syntax;

import java.util.ArrayList;
import java.util.List;

import jena.lang.EmptyGenericFlow;
import jena.lang.GenericFlow;
import jena.lang.ListGenericFlow;
import jena.lang.source.SingleCharacterKind;
import jena.lang.source.SourceSpan;

public final class ExpressionListSyntaxRule
{
    public interface ArgumentListSpanAction
    {
        void call(GenericFlow<Syntax> arguments, SourceSpan span);
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
                action.call(new ListGenericFlow<Syntax>(expressions), expressionSpan.skip(1));
            }
            else
            {
                new SyntaxGuess(expressionSpan, rule).guess(this);
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
            if(span.at(1).isKind(new SingleCharacterKind(')')))
            {
                action.call(new EmptyGenericFlow<Syntax>(), span.skip(2));
            }
            else new SyntaxGuess(span.skip(1), rule).guess(new ExpressionListMatchAction(action));
        }
    }
}