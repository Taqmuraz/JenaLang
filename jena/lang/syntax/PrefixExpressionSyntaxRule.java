package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.text.Text;

public class PrefixExpressionSyntaxRule implements SyntaxRule
{
    private Text prefix;
    private Text methodName;

    public PrefixExpressionSyntaxRule(Text prefix, Text methodName)
    {
        this.prefix = prefix;
        this.methodName = methodName;
    }

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        if(span.at(0).text().compare(prefix))
        {
            new AnyExpressionSyntaxRule().match(span.skip(1), (expression, expressionSpan) ->
            {
                action.call(
                    new InvocationExpressionSyntax(
                        new InvocationExpressionSyntax(expression, new SymbolLiteralSyntax(methodName)),
                        new ParenthesizedSyntax(expression)), expressionSpan);
            }, mistakeAction);
        }
        else
        {
            mistakeAction.call(new WrongSourceMistake(span.at(0), prefix), span);
        }
    }
}