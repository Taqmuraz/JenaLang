package jena.lang.syntax;

import jena.lang.Action;
import jena.lang.source.SourceSpan;
import jena.lang.text.SyntaxText;

public final class BindingExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, Action mismatch)
    {
        new NameExpressionSyntaxRule().match(span, (name, next) ->
        {
            if(next.at(0).text().compareString(":"))
            {
                action.call(new BindingConstructorExpressionSyntax(new SyntaxText(name)), next.skip(1));
            }
            else mismatch.call();
        },
        mismatch);
    }
}