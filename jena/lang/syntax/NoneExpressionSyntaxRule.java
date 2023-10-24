package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.text.StringText;

public final class NoneExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        if(span.at(0).text().compareString("()")) action.call(new NoneValueSyntax(), span.skip(1));
        else mistakeAction.call(new WrongSourceMistake(span.at(0), new StringText("()")), span);
    }
}