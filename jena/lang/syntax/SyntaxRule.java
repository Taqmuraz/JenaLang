package jena.lang.syntax;

import jena.lang.Action;
import jena.lang.source.SourceSpan;

public interface SyntaxRule
{
    void match(SourceSpan span, SyntaxSpanAction action, Action mismatch);
}