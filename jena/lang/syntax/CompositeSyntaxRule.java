package jena.lang.syntax;

import java.util.List;

import jena.lang.Action;
import jena.lang.GenericList;
import jena.lang.source.SourceSpan;

public class CompositeSyntaxRule implements SyntaxRule
{
    private GenericList<SyntaxRule> rules;

    public CompositeSyntaxRule(GenericList<SyntaxRule> rules)
    {
        this.rules = rules;
    }

    public CompositeSyntaxRule(SyntaxRule... rules)
    {
        this.rules = GenericList.of(List.of(rules));
    }

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, Action mismatch)
    {
        rules.read((f, r) ->
        {
            f.match(span, action, () -> new CompositeSyntaxRule(r).match(span, action, mismatch));
        },
        mismatch);
    }
}