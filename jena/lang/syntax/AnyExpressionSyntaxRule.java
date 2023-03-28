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
            new ClassClassicExpressionSyntaxRule(),
            new NegativeExpressionSyntaxRule(),
            new NotExpressionSyntaxRule()
        ),
        new AnyContinuousSyntaxRule()
    ), new CompositeSyntaxRule
    (
        new ArrowMethodSyntaxRule(),
        new ArrowClassSyntaxRule(),
        new ArrowUsingExpressionSyntaxRule(),
        new ClassSingleMemberExpressionSyntaxRule(),
        new MethodExpressionSyntaxRule(),
        new UsingExpressionSyntaxRule()
    ));

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        rule.match(span, action, mistakeAction);
    }
}