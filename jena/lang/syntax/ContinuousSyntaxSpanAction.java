package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public interface ContinuousSyntaxSpanAction
{
    void call(Syntax syntax, SourceSpan span, ContinuousSyntaxRule continued);
}