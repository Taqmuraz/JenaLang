package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public class AnyExpressionSyntaxRule implements SyntaxRule
{
    private static final SyntaxRule rule = new CompositeSyntaxRule(new ContinuedSyntaxRule
    (
        new CompositeSyntaxRule
        (
            new IntegerLiteralSyntaxRule(),
            new TextLiteralExpressionSyntaxRule(),
            new NameExpressionSyntaxRule(),
            new ParenthesizedExpressionSyntaxRule(),
            new ArrayExpressionSyntaxRule(),
            new ClassExpressionSyntaxRule()
        ),
        new CompositeContinuousSyntaxRule
        (
            new BinaryExpressionSyntaxRule(),
            new MemberAccessExpressionSyntaxRule(),
            new InvocationExpressionSyntaxRule()
        )
    ), new CompositeSyntaxRule
    (
        new LambdaExpressionSyntaxRule(),
        new MethodExpressionSyntaxRule(),
        new UsingExpressionSyntaxRule()
    ));

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action)
    {
        rule.match(span, action);
    }
}