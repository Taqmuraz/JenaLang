package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.Text;

public final class ParenthesizedExpressionSyntaxRule implements SyntaxRule
{

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        Text open = new SingleCharacterText('(');
        Text close = new SingleCharacterText(')');

        if(span.at(0).text().compare(open))
        {
            new AnyExpressionSyntaxRule().match(span.skip(1), (syntax, endSpan) ->
            {
                if (endSpan.at(0).text().compare(close)) action.call(new ParenthesizedSyntax(syntax), endSpan.skip(1));
                else mistakeAction.call(new WrongSourceMistake(endSpan.at(0), close), endSpan);
            }, mistakeAction);
        }
        else
        {
            mistakeAction.call(new WrongSourceMistake(span.at(0), open), span);
        }
    }
}