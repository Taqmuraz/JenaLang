package jena.lang.syntax;

import jena.lang.GenericBuffer;
import jena.lang.source.SourceSpan;

public interface SyntaxListSpanAction
{
        void call(GenericBuffer<Syntax> arguments, SourceSpan span);
}