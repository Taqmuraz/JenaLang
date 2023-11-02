package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public class LowerExpressionSyntaxRule implements SyntaxRule
{
    private static final SyntaxRule rule = new CompositeSyntaxRule(
        new NoneExpressionSyntaxRule(),
        new IntegerLiteralSyntaxRule(),
        new FloatLiteralSyntaxRule(),
        new SymbolLiteralSyntaxRule(),
        new OperatorLiteralSyntaxRule(),
        new TextLiteralExpressionSyntaxRule(),
        new NameExpressionSyntaxRule(),
        new ParenthesizedExpressionSyntaxRule(),
        new ArrayExpressionSyntaxRule(),
        new BindingListSyntaxRule(),
        new ArrowMethodSyntaxRule(),
        new BindingExpressionSyntaxRule(),
        new AssignmentExpressionSyntaxRule());

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        rule.match(span, action, mistakeAction);
    }
}