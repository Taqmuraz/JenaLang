package jena.syntax;

import java.util.Stack;

import jena.lang.source.SourceSpan;

public class JenaSyntaxReader
{
    SyntaxRule[] rules =
    {
        new IntegerLiteralSyntaxRule(),
        new BinaryExpressionSyntaxRule(),
    };

    public void read(SourceSpan span, SyntaxAction action)
    {
        Stack<Syntax> matches = new Stack<Syntax>();
        for(SyntaxRule rule : rules)
        {
            rule.match(span, (syntax, endSpan) -> matches.push(syntax));
        }
        if(!matches.isEmpty()) action.call(matches.pop());
    }
}