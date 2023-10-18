package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.text.StringText;
import jena.lang.text.SyntaxText;

public final class SymbolLiteralSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        if(span.at(0).text().compareString("."))
        {
            new NameExpressionSyntaxRule().match(span.skip(1), (name, nameSpan) ->
            {
                action.call(new SymbolLiteralSyntax(new SyntaxText(name)), nameSpan);
            }, mistakeAction);
        }
        else mistakeAction.call(new KindSourceMistake(span.at(0), new StringText(".")), span);
    }
}