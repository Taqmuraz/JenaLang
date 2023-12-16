package jena.lang.syntax;

import java.util.Map;

import jena.lang.Optional;
import jena.lang.source.SourceSpan;
import jena.lang.text.StringText;
import jena.lang.text.Text;

public final class OperatorSyntaxRule implements SyntaxRule
{
    static Map<String, String> operatorMap = Map.ofEntries
    (
        Map.entry("+", "add"),
        Map.entry("-", "sub"),
        Map.entry("*", "mul"),
        Map.entry("/", "div"),
        Map.entry("%", "mod"),
        Map.entry("!", "not"),
        Map.entry("&", "and"),
        Map.entry("|", "or"),
        Map.entry("==", "equals"),
        Map.entry("!=", "notEquals"),
        Map.entry(">", "greater"),
        Map.entry("<", "less")
    );

    @Override
    public Optional<SyntaxSpan> match(SourceSpan span)
    {
        Text operator = span.at(0).text();
        String name = operatorMap.get(operator.string());
        if(name == null) return Optional.no();
        return Optional.yes(SyntaxSpan.of(new SymbolSyntax(new StringText(name)), span.skip(1)));
    }
}