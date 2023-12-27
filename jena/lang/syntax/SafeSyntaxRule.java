package jena.lang.syntax;

import jena.lang.Optional;
import jena.lang.source.SourceSpan;

public class SafeSyntaxRule implements SyntaxRule
{
    SyntaxRule source;

    public SafeSyntaxRule(SyntaxRule source)
    {
        this.source = source;
    }

    @Override
    public Optional<SyntaxSpan> match(SourceSpan span)
    {
        return source.match(span).map(ss -> ss.map((syntax, nextSpan) -> SyntaxSpan.of(new SafeSyntax(syntax, span, nextSpan), nextSpan)));
    }
}