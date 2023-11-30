package jena.lang.syntax;

import jena.lang.Optional;
import jena.lang.source.SourceSpan;

public final class EntrySyntaxRule implements SyntaxRule
{
    SyntaxRule base;

    public EntrySyntaxRule(SyntaxRule base)
    {
        this.base = base;
    }

    @Override
    public Optional<SyntaxSpan> match(SourceSpan span)
    {
        return base.match(span).mapOptional(key ->
        {
            var keyPair = key.pair();
            if(keyPair.b.at(0).text().compareString(":"))
            {
                return Optional.yes(SyntaxSpan.of(new EntrySyntax(keyPair.a), keyPair.b.skip(1)));
            }
            return Optional.yes(key);
        });
    }
}