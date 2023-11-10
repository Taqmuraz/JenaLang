package jena.lang.syntax;

import jena.lang.Optional;
import jena.lang.source.SourceSpan;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.Text;

public final class TextLiteralSyntaxRule implements SyntaxRule
{
    @Override
    public Optional<SyntaxSpan> match(SourceSpan span)
    {
        Text symbol = new SingleCharacterText('\"');
        if(span.at(0).text().compare(symbol) && span.at(2).text().compare(symbol))
        {
            return Optional.yes(SyntaxSpan.of(new TextLiteralSyntax(span.at(1).text()), span.skip(3)));
        }
        return Optional.no();
    }
}