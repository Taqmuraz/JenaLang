package jena.lang.syntax;

import java.util.Map;

import jena.lang.source.SourceSpan;
import jena.lang.text.StringText;
import jena.lang.text.Text;

public final class OperatorLiteralSyntaxRule implements SyntaxRule
{
    static Map<String, String> operatorMap = Map.ofEntries
    (
        Map.entry("+", "add"),
        Map.entry("-", "sub"),
        Map.entry("*", "mul"),
        Map.entry("/", "div"),
        Map.entry("!", "not"),
        Map.entry("&", "and"),
        Map.entry("|", "or"),
        Map.entry("==", "equals"),
        Map.entry("!=", "notEquals"),
        Map.entry(">", "greater"),
        Map.entry("<", "less")
    );

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        Text operator = span.at(0).text();
        String name = operatorMap.get(operator.string());
        if(name == null) mistakeAction.call(new WrongSourceMistake(span.at(0), operator), span);
        else action.call(new SymbolLiteralSyntax(new StringText(name)), span.skip(1));
    }
}