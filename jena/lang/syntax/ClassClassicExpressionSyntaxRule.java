package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public final class ClassClassicExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action)
    {
        new ClassExpressionSyntaxRule(
            new ParameterListSyntaxRule(),
            new MemberListSyntaxRule()
        ).match(span, action);
    }
}