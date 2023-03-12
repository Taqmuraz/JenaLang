package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public final class ClassSingleMemberExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        new ClassExpressionSyntaxRule(
            new CompositeSyntaxListRule(
                new ParameterListSyntaxRule(),
                new EmptyListSyntaxRule()),
            new SingleListSyntaxRule(new MemberExpressionSyntaxRule())
        ).match(span, action, mistakeAction);
    }
}