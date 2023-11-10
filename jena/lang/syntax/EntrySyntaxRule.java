package jena.lang.syntax;

import jena.lang.Optional;
import jena.lang.source.SourceSpan;
import jena.lang.text.Text;

public final class EntrySyntaxRule implements SyntaxRule
{
    @Override
    public Optional<SyntaxSpan> match(SourceSpan span)
    {
        return SyntaxRule.name.match(span).mapOptional(name ->
        {
            var namePair = name.pair();
            if(namePair.b.at(0).text().compareString(":"))
            {
                return Optional.yes(SyntaxSpan.of(new EntrySyntax(Text.of(namePair.a)), namePair.b.skip(1)));
            }
            return Optional.no();
        });
    }
}