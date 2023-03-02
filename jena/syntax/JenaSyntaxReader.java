package jena.syntax;

import jena.lang.source.SourceSpan;

public class JenaSyntaxReader
{
    SyntaxRule[] rules =
    {
        new AddExpressionSyntaxRule(),
        new IntegerLiteralSyntaxRule(),
    };

    public void read(SourceSpan span, SyntaxAction action)
    {
    }
}