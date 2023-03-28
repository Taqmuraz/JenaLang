package jena.lang.syntax;

import jena.lang.GenericBuffer;
import jena.lang.GenericFlow;
import jena.lang.source.SourceSpan;
import jena.lang.text.Text;

public class ArrowUsingExpressionSyntaxRule implements SyntaxRule
{
    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        new ArrowExpressionSyntaxRule(
            new MemberExpressionSyntaxRule(),
            new AnyExpressionSyntaxRule()
        ).match(span, (usings, expression, endSpan) ->
        {
            GenericFlow<MemberExpressionSyntax> members = usings.flow().map(u -> (MemberExpressionSyntax)u);
            GenericBuffer<Text> names = members.map(m -> m.nameSyntax().struct().a).collect();
            GenericBuffer<Syntax> expressions = members.map(m -> m.nameSyntax().struct().b).collect();
            action.call(new UsingExpressionSyntax(expressions, names, expression), endSpan);
        }, mistakeAction);
    }
}