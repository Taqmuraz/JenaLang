package jena.lang.syntax;

import jena.lang.source.SourceSpan;
import jena.lang.GenericAction;
import jena.lang.GenericList;

public class InvocationExpressionSyntaxRule implements SyntaxRule
{
    SyntaxRule lowerRule = new LowerExpressionSyntaxRule();

    @Override
    public void match(SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        lowerRule.match(span, (syntax, next) ->
        {
            matchNext(GenericList.single(syntax), next, action, mistakeAction);
        }, mistakeAction);
    }

    void matchNext(GenericList<Syntax> list, SourceSpan span, SyntaxSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        list.read((first, rest) ->
        {
            Syntax[] result = { first };
            rest.read(listReader(s -> result[0] = new InvocationExpressionSyntax(result[0], s)));
            action.call(result[0], span);
        });
        lowerRule.match(span, (syntax, next) ->
        {
            matchNext(list.append(syntax), next, action, mistakeAction);
        }, mistakeAction);
    }
    static GenericList.FirstRestAction<Syntax> listReader(GenericAction<Syntax> action)
    {
        return (h, t) ->
        {
            action.call(h);
            t.read(listReader(action));
        };
    }
}
