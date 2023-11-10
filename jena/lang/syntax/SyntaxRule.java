package jena.lang.syntax;

import jena.lang.Optional;
import jena.lang.source.SourceSpan;

public interface SyntaxRule
{
    Optional<SyntaxSpan> match(SourceSpan span);
}