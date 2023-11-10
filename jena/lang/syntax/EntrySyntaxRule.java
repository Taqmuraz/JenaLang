package jena.lang.syntax;

import jena.lang.Optional;
import jena.lang.source.SourceSpan;
import jena.lang.text.Text;

public final class EntrySyntaxRule implements SyntaxRule
{
    @Override
    public Optional<SyntaxSpan> match(SourceSpan span)
    {
        return new NameSyntaxRule().match(span).map(ss -> ss.map((name, nameSpan) ->
        {
            if(nameSpan.at(0).text().compareString(":"))
            {
                return SyntaxSpan.of(new EntrySyntax(Text.of(name)), nameSpan.skip(1));
            }
            return ss;
        }));
    }
}