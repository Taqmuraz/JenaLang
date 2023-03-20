package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public interface ContinuousSyntaxRule
{
    void match(SourceSpan span, Syntax last, ContinuousSyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction);
}