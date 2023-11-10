package jena.lang.syntax;

import jena.lang.Optional;
import jena.lang.source.SourceSpan;

public final class NameSyntaxRule implements SyntaxRule
{
    @Override
    public Optional<SyntaxSpan> match(SourceSpan span)
    {
        if(span.at(0).isKind(Character::isAlphabetic))
        {
            return Optional.yes(SyntaxSpan.of(new NameSyntax(span.at(0).text()), span.skip(1)));
        }
        return Optional.no();
    }
}