package jena.lang.syntax;

import jena.lang.Optional;
import jena.lang.source.SourceSpan;
import jena.lang.text.Text;

public final class ParenthesizedSyntaxRule implements SyntaxRule
{
    @Override
    public Optional<SyntaxSpan> match(SourceSpan span)
    {
        if(span.at(0).text().compare(Text.of('(')))
        {
            return SyntaxRule.any().match(span.skip(1)).mapOptional(ss ->
            {
                var pair = ss.pair();
                if(pair.b.at(0).text().compare(Text.of(')')))
                {
                    return Optional.yes(SyntaxSpan.of(new ParenthesizedSyntax(pair.a), pair.b.skip(1)));
                }
                return Optional.no();
            });
        }
        return Optional.no();
    }   
}