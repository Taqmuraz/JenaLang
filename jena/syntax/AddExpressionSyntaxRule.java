package jena.syntax;

import jena.lang.source.SingleCharacterKind;
import jena.lang.source.SourceSpan;

public class AddExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan sourceSpan, SyntaxSpanAction action)
    {
        SyntaxRule literal = new IntegerLiteralSyntaxRule();
        Syntax[] children = new Syntax[2];
        SourceSpan[] span = { sourceSpan };
        literal.match(span[0], (syntax, s) ->
        {
            children[0] = syntax;
            span[0] = s;
        });
        if (span[0].at(0).isKind(new SingleCharacterKind('+')))
        {
            literal.match(span[0], (syntax, s) ->
            {
                children[1] = syntax;
                span[0] = s;
            });
            action.call(new AddExpressionSyntax(children[0], children[1]), span[0]);
        }
    }
}