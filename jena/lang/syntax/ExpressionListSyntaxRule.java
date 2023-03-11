package jena.lang.syntax;

import java.util.ArrayList;
import java.util.List;

import jena.lang.EmptyGenericBuffer;
import jena.lang.ListGenericBuffer;
import jena.lang.source.CharacterBuffer;
import jena.lang.source.SingleCharacterKind;
import jena.lang.source.Source;
import jena.lang.source.SourceSpan;

public final class ExpressionListSyntaxRule implements SyntaxListRule
{
    private class ExpressionListMatchAction implements SyntaxSpanAction
    {
        List<Syntax> expressions = new ArrayList<Syntax>();
        SyntaxListSpanAction action;
        
        public ExpressionListMatchAction(SyntaxListSpanAction action)
        {
            this.action = action;
        }

        @Override
        public void call(Syntax expression, SourceSpan expressionSpan)
        {
            expressions.add(expression);
            if(expressionSpan.at(0).text().compare(closeBrace))
            {
                action.call(new ListGenericBuffer<Syntax>(expressions), expressionSpan.skip(1));
            }
            else if(expressionSpan.at(0).isKind(new SingleCharacterKind(',')))
            {
                new SyntaxGuess(expressionSpan.skip(1), rule).guess(this);
            }
        }
    }

    private SyntaxRule rule;
    private CharacterBuffer openBrace;
    private CharacterBuffer closeBrace;

    public ExpressionListSyntaxRule(SyntaxRule rule, Source openBrace, Source closeBrace)
    {
        this.rule = rule;
        this.openBrace = openBrace.text();
        this.closeBrace = closeBrace.text();
    }

    public void match(SourceSpan span, SyntaxListSpanAction action)
    {
        if(span.at(0).text().compare(openBrace))
        {
            if(span.at(1).text().compare(closeBrace))
            {
                action.call(new EmptyGenericBuffer<Syntax>(), span.skip(2));
            }
            else new SyntaxGuess(span.skip(1), rule).guess(new ExpressionListMatchAction(action));
        }
    }
}