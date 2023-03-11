package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public interface SyntaxListRule
{
    void match(SourceSpan span, SyntaxListSpanAction action);
}