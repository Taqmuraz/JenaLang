package jena.lang.syntax;

import jena.lang.Optional;
import jena.lang.source.SourceSpan;

public final class NoneSyntaxRule implements SyntaxRule
{
    @Override
    public Optional<SyntaxSpan> match(SourceSpan span)
    {
        if(span.at(0).text().compareString("(") && span.at(1).text().compareString(")"))
            return Optional.yes(SyntaxSpan.of(new NoneValueSyntax(), span.skip(2)));
        return Optional.no();
    }
}