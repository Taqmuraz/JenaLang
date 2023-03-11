package jena.lang.syntax;

import jena.lang.GenericBuffer;
import jena.lang.source.SourceSpan;

public interface ArrowSpanAction
{
    void call(GenericBuffer<Syntax> arguments, Syntax expression, SourceSpan span);
}