package jena.syntax;

import jena.lang.source.SourceSpan;

public interface SyntaxRule
{
    void match(SourceSpan span, SyntaxSpanAction action);
}