package jena.lang.syntax;


import jena.lang.Action;
import jena.lang.source.SourceSpan;

public class CompositeSyntaxRule implements SyntaxRule
{
    private SyntaxRule[] rules;

    public CompositeSyntaxRule(SyntaxRule... rules)
    {
        this.rules = rules;
    }

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, Action mismatch)
    {
        boolean[] hasMatch = { false };
        int index = 0;
        do
        {
            rules[index++].match(span, (syntax, nextSpan) ->
            {
                hasMatch[0] = true;
                action.call(syntax, nextSpan);
            },
            () -> { });
        }
        while(!hasMatch[0] && index < rules.length);
        if(!hasMatch[0])
        {
            mismatch.call();
        }
    }
}