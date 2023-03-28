package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.SyntaxText;
import jena.lang.text.Text;

public final class PairExpressionSyntaxRule implements SyntaxRule
{
    private Text symbol;

    public PairExpressionSyntaxRule(Text symbol) {
        this.symbol = symbol;
    }

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        new NameExpressionSyntaxRule().match(span, (name, nameSpan) ->
        {
            if(nameSpan.at(0).text().compare(symbol))
            {
                new AnyExpressionSyntaxRule().match(nameSpan.skip(1), (expression, expressionSpan) ->
                {
                    action.call(new PairExpressionSyntax(new SyntaxText(name), expression), expressionSpan);
                }, mistakeAction);
            }
            else
            {
                mistakeAction.call(new WrongSourceMistake(nameSpan.at(0), new SingleCharacterText(':')), nameSpan);
            }
        }, mistakeAction);
    }
}