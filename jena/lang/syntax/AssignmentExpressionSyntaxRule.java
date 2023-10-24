package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.text.SingleCharacterText;

public class AssignmentExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        new ArrowExpressionSyntaxRule(
            new BindingSyntaxRule(new SingleCharacterText('=')),
            new AnyExpressionSyntaxRule()
        ).match(span, (name, expression, endSpan) ->
        {
            var binding = ((BindingExpressionSyntax)name).nameSyntax().struct();
            action.call(new AssignmentExpressionSyntax(binding.b, binding.a, expression), endSpan);
        }, mistakeAction);
    }
}