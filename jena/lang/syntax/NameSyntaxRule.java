package jena.lang.syntax;

import jena.lang.Optional;
import jena.lang.source.SourceSpan;

public final class NameSyntaxRule implements SyntaxRule
{
    @Override
    public Optional<SyntaxSpan> match(SourceSpan span)
    {
        var element = span.at(0);
        var text = element.text();
        if(text.length() != 0 && Character.isJavaIdentifierStart(text.at(0)) && element.isKind(Character::isJavaIdentifierPart))
        {
            return Optional.yes(SyntaxSpan.of(new NameSyntax(span.at(0).text()), span.skip(1)));
        }
        return Optional.no();
    }
}