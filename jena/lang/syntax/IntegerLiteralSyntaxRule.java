package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public class IntegerLiteralSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        if(span.at(0).isKind(Character::isDigit))
        {
            action.call(new IntegerLiteralSyntax(span.at(0).text()), span.skip(1));
        }
    }
}