package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public interface SyntaxSpanAction
{
    void call(Syntax syntax, SourceSpan span);
}