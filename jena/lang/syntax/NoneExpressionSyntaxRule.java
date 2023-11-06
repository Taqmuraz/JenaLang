package jena.lang.syntax;

import jena.lang.Action;
import jena.lang.source.SourceSpan;

public final class NoneExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, Action mismatch)
    {
        if(span.at(0).text().compareString("(") && span.at(1).text().compareString(")")) action.call(new NoneValueSyntax(), span.skip(2));
        else mismatch.call();
    }
}