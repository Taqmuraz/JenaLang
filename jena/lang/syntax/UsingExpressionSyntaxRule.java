package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public final class UsingExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action)
    {
        if(span.at(0).text().compareString("using"))
        {
            new ArgumentListSyntaxRule().match(span.skip(1), (expressions, expressionsSpan) ->
            {
                if(expressionsSpan.at(0).text().compareString("as"))
                {
                    new ParameterListSyntaxRule().match(expressionsSpan.skip(1), (names, namesSpan) ->
                    {
                        new AnyExpressionSyntaxRule().match(namesSpan, (expression, exSpan) ->
                        {
                            action.call(new UsingExpressionSyntax(expressions, names, expression), exSpan);
                        });
                    });
                }
            });
        }
    }
}