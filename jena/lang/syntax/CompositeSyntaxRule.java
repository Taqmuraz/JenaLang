package jena.lang.syntax;

import jena.lang.Optional;
import jena.lang.source.SourceSpan;

public class CompositeSyntaxRule implements SyntaxRule
{
    private SyntaxRule[] rules;

    public CompositeSyntaxRule(SyntaxRule... rules)
    {
        this.rules = rules;
    }

    @Override
    public Optional<SyntaxSpan> match(SourceSpan span)
    {
        boolean[] hasMatch = { false };
        int index = 0;
        Optional<SyntaxSpan> result = Optional.no();
        do
        {
            result = rules[index++].match(span);
            result.ifPresent(item -> hasMatch[0] = true);
        }
        while(!hasMatch[0] && index < rules.length);
        return result;
    }
}