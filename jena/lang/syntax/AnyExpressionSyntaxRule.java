package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public class AnyExpressionSyntaxRule implements SyntaxRule
{
    private static final SyntaxRule rule = new CompositeSyntaxRule(new ContinuedSyntaxRule
    (
        new CompositeSyntaxRule
        (
            new IntegerLiteralSyntaxRule(),
            new SymbolLiteralSyntaxRule(),
            new TextLiteralExpressionSyntaxRule(),
            new NameExpressionSyntaxRule(),
            new ParenthesizedExpressionSyntaxRule(),
            new ArrayExpressionSyntaxRule(),
            new NegativeExpressionSyntaxRule(),
            new BindingExpressionSyntaxRule(),
            new NotExpressionSyntaxRule(),
            new TupleExpressionSyntaxRule(),
            new BindingListSyntaxRule(),
            new BindingExpressionSyntaxRule()
        ),
        new AnyContinuousSyntaxRule()
    ), new CompositeSyntaxRule
    (
        new ArrowMethodSyntaxRule(),
        new ArrowUsingExpressionSyntaxRule(),
        new MethodExpressionSyntaxRule(),
        new UsingExpressionSyntaxRule()
    ));

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        rule.match(span, action, mistakeAction);
    }
}