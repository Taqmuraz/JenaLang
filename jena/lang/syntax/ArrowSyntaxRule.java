package jena.lang.syntax;

import jena.lang.Optional;
import jena.lang.source.SourceSpan;
import jena.lang.text.Text;

public final class ArrowSyntaxRule implements SyntaxRule
{
    @Override
    public Optional<SyntaxSpan> match(SourceSpan span)
    {
        return new CompositeSyntaxRule(SyntaxRule.name, SyntaxRule.none).match(span).mapOptional(name ->
        {
            var namePair = name.pair();
            if(namePair.b.at(0).text().compare(Text.of("->")))
            {
                return SyntaxRule.any().match(namePair.b.skip(1)).mapOptional(expression ->
                {
                    var exPair = expression.pair();
                    return Optional.yes(SyntaxSpan.of(new ArrowSyntax(namePair.a, exPair.a), exPair.b));
                });
            }
            return Optional.no();
        });
    }
}