package jena.lang.syntax;

import jena.lang.source.Source;
import jena.lang.source.SourceFlow;
import jena.lang.source.SourceSpan;
import jena.lang.text.Text;

import java.util.Stack;

import jena.lang.Action;
import jena.lang.GenericList;
import jena.lang.source.JenaSourceFlow;

public class JenaSyntaxReader
{
    private static final SyntaxRule syntaxRule = new CompositeSyntaxRule(
        new NoneExpressionSyntaxRule(),
        new IntegerLiteralSyntaxRule(),
        new FloatLiteralSyntaxRule(),
        new SymbolLiteralSyntaxRule(),
        new OperatorLiteralSyntaxRule(),
        new TextLiteralExpressionSyntaxRule(),
        new NameExpressionSyntaxRule());

    private static final SyntaxStackRule stackRule = SyntaxStackRule.of(GenericList.of(
        SyntaxStackRule.addSyntaxRule(syntaxRule),
        SyntaxStackRule.pushRule(Text.of('('), SyntaxList::invocationList),
        SyntaxStackRule.pushNestedRule(Text.of('['), SyntaxList::arrayList, SyntaxList::invocationList),
        SyntaxStackRule.pushNestedRule(Text.of('{'), SyntaxList::mapList, SyntaxList::invocationList),
        SyntaxStackRule.popRule(Text.of(')')),
        SyntaxStackRule.popNestedRule(Text.of(']')),
        SyntaxStackRule.popNestedRule(Text.of('}')),
        SyntaxStackRule.resetRule(Text.of(',')),
        SyntaxStackRule.resetRule(Text.of("->"))
    ));

    private SourceFlow flow;
    
    public JenaSyntaxReader(Source source)
    {
        flow = new JenaSourceFlow(source);
    }

    public void read(SyntaxAction action, SyntaxMistakeAction mistakeAction)
    {
        Action mismatch = () ->
        {
            mistakeAction.call(writer -> writer.write("No syntax matched"));
        };
        SourceSpan start = flow.span();
        Stack<SyntaxList> stack = new Stack<>();
        stack.push(SyntaxList.invocationList());
        matchStack(stack, start, mismatch);
        action.call(stack.peek().complete());
    }
    void matchStack(Stack<SyntaxList> stack, SourceSpan span, Action mismatch)
    {
        if(span.at(0).isEmpty()) return;
        stackRule.match(span, syntax ->
        {
            stack.peek().add(() -> syntax);
        },
        list ->
        {
            stack.peek().add(list::complete);
            stack.push(list);
        },
        stack::pop,
        stack::peek,
        nextSpan -> matchStack(stack, nextSpan, mismatch),
        mismatch);
    }
}