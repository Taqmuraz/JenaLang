package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.text.SingleCharacterText;

public final class BindingExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        new PairExpressionSyntaxRule(new SingleCharacterText(':')).match(span, action, mistakeAction);
    }
}