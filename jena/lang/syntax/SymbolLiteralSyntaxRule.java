package jena.lang.syntax;

import jena.lang.Optional;
import jena.lang.source.SourceSpan;
import jena.lang.text.SyntaxText;

public final class SymbolLiteralSyntaxRule implements SyntaxRule
{
    @Override
    public Optional<SyntaxSpan> match(SourceSpan span)
    {
        if(span.at(0).text().compareString("."))
        {
            return new NameExpressionSyntaxRule().match(span.skip(1)).map(ss -> ss.map((name, nameSpan) ->
            {
                return SyntaxSpan.of(new SymbolLiteralSyntax(new SyntaxText(name)), nameSpan);
            }));
        }
        return Optional.no();
    }
}