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
        int index = 0;
        Optional<SyntaxSpan> result = Optional.no();
        do
        {
            result = rules[index++].match(span);
        }
        while(!result.present() && index < rules.length);
        return result;
    }
}