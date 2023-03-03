package jena.syntax;

import jena.lang.source.SourceSpan;

public class JenaSyntaxReader
{
    SyntaxRule rule = new CompositeSyntaxRule
    (
        new IntegerLiteralSyntaxRule(),
        new BinaryExpressionSyntaxRule()
    );

    public void read(SourceSpan span, SyntaxAction action)
    {
        new SyntaxGuess(span, rule).guess((syntax, s) -> action.call(syntax));
    }
}