package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public class AnyExpressionSyntaxRule implements SyntaxRule
{
    private static final SyntaxRule rule = new ContinuedSyntaxRule
    (
        new CompositeSyntaxRule
        (
            new IntegerLiteralSyntaxRule(),
            new TextLiteralExpressionSyntaxRule(),
            new NameExpressionSyntaxRule(),
            new ParenthesizedExpressionSyntaxRule(),
            new UsingExpressionSyntaxRule(),
            new MethodExpressionSyntaxRule(),
            new ArrayExpressionSyntaxRule(),
            new LambdaExpressionSyntaxRule(),
            new ClassExpressionSyntaxRule()
        ),
        new CompositeContinuousSyntaxRule
        (
            new BinaryExpressionSyntaxRule(),
            new MemberAccessExpressionSyntaxRule(),
            new InvocationExpressionSyntaxRule()
        )
    );

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action)
    {
        rule.match(span, action);
    }
}