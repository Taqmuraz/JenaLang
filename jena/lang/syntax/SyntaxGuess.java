package jena.lang.syntax;

import java.util.Stack;

import jena.lang.Action;
import jena.lang.source.SourceSpan;

public class SyntaxGuess
{
    private SourceSpan span;
    private SyntaxRule rule;

    public SyntaxGuess(SourceSpan span, SyntaxRule rule)
    {
        this.span = span;
        this.rule = rule;
    }

    public void guess(SyntaxSpanAction action)
    {
        Stack<Action> matches = new Stack<Action>();
        rule.match(span, (syntax, span) -> matches.push(() -> action.call(syntax, span)));
        if(!matches.isEmpty()) matches.pop().call();
    }
}