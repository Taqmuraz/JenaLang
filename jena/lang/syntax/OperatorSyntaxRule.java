package jena.lang.syntax;

import jena.lang.source.SingleCharacterKind;
import jena.lang.source.SourceSpan;

public final class OperatorSyntaxRule implements SyntaxRule
{
    private char operator;

    public OperatorSyntaxRule(char operator)
    {
        this.operator = operator;
    }

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action)
    {
        if(span.at(0).isKind(new SingleCharacterKind(operator)))
        {
            action.call(new MathBinaryOperatorSyntax(operator), span.skip(1));
        }
    }
}