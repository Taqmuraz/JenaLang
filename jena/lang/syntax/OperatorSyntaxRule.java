package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.text.Text;

public final class OperatorSyntaxRule implements SyntaxRule
{
    private Text operator;

    public OperatorSyntaxRule(Text operator)
    {
        this.operator = operator;
    }

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        if(span.at(0).text().compare(operator))
        {
            action.call(new MathBinaryOperatorSyntax(operator), span.skip(1));
        }
        else
        {
            mistakeAction.call(new WrongSourceMistake(span.at(0), operator), span);
        }
    }
}