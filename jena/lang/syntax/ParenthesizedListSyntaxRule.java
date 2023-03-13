package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.Text;

public final class ParenthesizedListSyntaxRule implements SyntaxListRule
{
    private SyntaxRule elementRule;

    public ParenthesizedListSyntaxRule(SyntaxRule elementRule)
    {
        this.elementRule = elementRule;
    }

    @Override
    public void match(SourceSpan span, SyntaxListSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        Text openBrace = new SingleCharacterText('(');
        Text closeBrace = new SingleCharacterText(')');
        new ExpressionListSyntaxRule(elementRule, openBrace, closeBrace).match(span, action, mistakeAction);
    }
}