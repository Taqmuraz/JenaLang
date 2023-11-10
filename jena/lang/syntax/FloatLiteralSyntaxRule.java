package jena.lang.syntax;

import jena.lang.Optional;
import jena.lang.source.SourceSpan;
import jena.lang.text.ConcatText;

public class FloatLiteralSyntaxRule implements SyntaxRule
{
    @Override
    public Optional<SyntaxSpan> match(SourceSpan span)
    {
        if(span.at(0).isKind(Character::isDigit) && span.at(1).text().compareString(".") && span.at(2).isKind(Character::isDigit))
        {
            return Optional.yes(SyntaxSpan.of(new FloatLiteralSyntax(
                new ConcatText(
                    span.at(0).text(),
                    new ConcatText(
                        span.at(1).text(),
                        span.at(2).text()))),
                    span.skip(3)));
        }
        return Optional.no();
    }
}