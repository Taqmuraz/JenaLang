package jena.syntax;

import jena.lang.source.SingleCharacterKind;
import jena.lang.source.SourceSpan;

public final class ParenthesizedExpressionSyntaxRule implements SyntaxRule
{

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action)
    {
        if(span.at(0).isKind(new SingleCharacterKind('(')))
        {
            new AnyExpressionSyntaxRule().match(span.skip(1), (syntax, endSpan) ->
            {
                if (endSpan.at(0).isKind(new SingleCharacterKind(')'))) action.call(new ParenthesizedSyntax(syntax), endSpan.skip(1));
            });
        }
    }
}