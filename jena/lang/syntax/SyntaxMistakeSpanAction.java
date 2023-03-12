package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public interface SyntaxMistakeSpanAction
{
    void call(SyntaxMistake mistake, SourceSpan span);
}