package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.StringText;

public class NegativeExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        new PrefixExpressionSyntaxRule(
            new SingleCharacterText('-'),
            new StringText("negative")
        ).match(span, action, mistakeAction);
    }
}