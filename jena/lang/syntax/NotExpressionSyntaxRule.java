package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.StringText;

public class NotExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        new PrefixExpressionSyntaxRule(
            new SingleCharacterText('!'),
            new StringText("not")
        ).match(span, action, mistakeAction);
    }
}