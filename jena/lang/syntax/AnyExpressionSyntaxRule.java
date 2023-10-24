package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public class AnyExpressionSyntaxRule implements SyntaxRule
{
    private static final SyntaxRule rule = new CompositeSyntaxRule(new ContinuedSyntaxRule
    (
        new CompositeSyntaxRule
        (
            new NoneExpressionSyntaxRule(),
            new IntegerLiteralSyntaxRule(),
            new FloatLiteralSyntaxRule(),
            new SymbolLiteralSyntaxRule(),
            new OperatorLiteralSyntaxRule(),
            new TextLiteralExpressionSyntaxRule(),
            new NameExpressionSyntaxRule(),
            new ParenthesizedExpressionSyntaxRule(),
            new ArrayExpressionSyntaxRule(),
            new ChainSyntaxRule(),
            new BindingListSyntaxRule()
        ),
        new AnyContinuousSyntaxRule()
    ), new CompositeSyntaxRule
    (
        new ArrowMethodSyntaxRule(),
        new BindingExpressionSyntaxRule(),
        new AssignmentExpressionSyntaxRule()
    ));

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        rule.match(span, action, mistakeAction);
    }
}