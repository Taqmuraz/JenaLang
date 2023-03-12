package jena.lang.syntax;

import jena.lang.source.SingleCharacterKind;
import jena.lang.source.SourceSpan;

public final class MemberAccessExpressionSyntaxRule implements ContinuousSyntaxRule
{
    @Override
    public void match(SourceSpan span, Syntax last, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        if(span.at(0).isKind(new SingleCharacterKind('.')))
        {
            action.call(new MemberAccessExpressionSyntax(last, span.at(1)), span.skip(2));
        }
    }
}