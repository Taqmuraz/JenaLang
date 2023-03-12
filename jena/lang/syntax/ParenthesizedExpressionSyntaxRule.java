package jena.lang.syntax;

import jena.lang.source.SingleCharacterSource;
import jena.lang.source.Source;
import jena.lang.source.SourceSpan;

public final class ParenthesizedExpressionSyntaxRule implements SyntaxRule
{

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        Source open = new SingleCharacterSource('(');
        Source close = new SingleCharacterSource(')');

        if(span.at(0).text().compare(open.text()))
        {
            new AnyExpressionSyntaxRule().match(span.skip(1), (syntax, endSpan) ->
            {
                if (endSpan.at(0).text().compare(close.text())) action.call(new ParenthesizedSyntax(syntax), endSpan.skip(1));
                else mistakeAction.call(new WrongSourceMistake(endSpan.at(0), close), endSpan);
            }, mistakeAction);
        }
        else
        {
            mistakeAction.call(new WrongSourceMistake(span.at(0), open), span);
        }
    }
}