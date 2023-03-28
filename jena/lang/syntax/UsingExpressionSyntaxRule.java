package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.text.StringText;
import jena.lang.text.SyntaxText;
import jena.lang.text.Text;

public final class UsingExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
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
                            action.call(new UsingExpressionSyntax(expressions, names.flow().<Text>map(SyntaxText::new).collect(), expression), exSpan);
                        }, mistakeAction);
                    }, mistakeAction);
                }
            }, mistakeAction);
        }
        else
        {
            mistakeAction.call(new WrongSourceMistake(span.at(0), new StringText("using")), span);
        }
    }
}