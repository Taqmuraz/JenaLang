package jena.lang.syntax;

import jena.lang.Action;
import jena.lang.source.SourceSpan;
import jena.lang.text.SyntaxText;

public final class SymbolLiteralSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, Action mismatch)
    {
        if(span.at(0).text().compareString("."))
        {
            new NameExpressionSyntaxRule().match(span.skip(1), (name, nameSpan) ->
            {
                action.call(new SymbolLiteralSyntax(new SyntaxText(name)), nameSpan);
            }, mismatch);
        }
        else mismatch.call();
    }
}