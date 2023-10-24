package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public interface ArrowSpanAction
{
    void call(Syntax argument, Syntax expression, SourceSpan span);
}