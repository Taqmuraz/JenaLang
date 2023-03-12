package jena.lang.syntax;

import jena.lang.source.Source;
import jena.lang.source.SourceSpan;

public final class OperatorSyntaxRule implements SyntaxRule
{
    private Source operator;

    public OperatorSyntaxRule(Source operator)
    {
        this.operator = operator;
    }

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        if(span.at(0).text().compare(operator.text()))
        {
            action.call(new MathBinaryOperatorSyntax(operator), span.skip(1));
        }
        else
        {
            mistakeAction.call(new WrongSourceMistake(span.at(0), operator), span);
        }
    }
}