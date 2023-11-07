package jena.lang.syntax;

import java.util.function.Consumer;
import java.util.function.Supplier;

import jena.lang.Action;
import jena.lang.source.SourceSpan;
import jena.lang.text.Text;

public interface SyntaxStackRule
{
    void match(
        SourceSpan span,
        SyntaxListStack stack,
        Consumer<SourceSpan> next,
        Action mismatch);

        
    static final SyntaxRule syntaxRule = new CompositeSyntaxRule(
        new NoneExpressionSyntaxRule(),
        new IntegerLiteralSyntaxRule(),
        new FloatLiteralSyntaxRule(),
        new SymbolLiteralSyntaxRule(),
        new OperatorLiteralSyntaxRule(),
        new TextLiteralExpressionSyntaxRule(),
        new BindingExpressionSyntaxRule(),
        new NameExpressionSyntaxRule());

    static final SyntaxStackRule stackRule = of(
        arrowRule(),
        assignmentRule(),
        addSyntaxRule(syntaxRule),
        pushRule(Text.of('('), SyntaxList::invocationList),
        pushNestedRule(Text.of('['), SyntaxList::arrayList, SyntaxList::invocationList),
        pushNestedRule(Text.of('{'), SyntaxList::mapList, SyntaxList::invocationList),
        popRule(Text.of(')')),
        popNestedRule(Text.of(']')),
        popNestedRule(Text.of('}')),
        commaRule()
    );

    static SyntaxStackRule pushRule(Text symbol, Supplier<SyntaxList> list)
    {
        return (span, stack, next, mismatch) ->
        {
            if(span.at(0).text().compare(symbol))
            {
                var nl = list.get();
                stack.peek().push(nl);
                stack.push(nl);
                next.accept(span.skip(1));
            }
            else mismatch.call();
        };
    }
    static SyntaxStackRule pushNestedRule(Text symbol, Supplier<SyntaxList> outer, Supplier<SyntaxList> inner)
    {
        return (span, stack, next, mismatch) ->
        {
            if(span.at(0).text().compare(symbol))
            {
                var newOuter = outer.get();
                var newInner = inner.get();
                newOuter.push(newInner);
                stack.peek().push(newOuter);
                stack.push(newOuter);
                stack.push(newInner);
                next.accept(span.skip(1));
            }
            else mismatch.call();
        };
    }
    static SyntaxStackRule popNestedRule(Text symbol)
    {
        return (span, stack, next, mismatch) ->
        {
            if(span.at(0).text().compare(symbol))
            {
                var list = stack.peek();
                stack.pop();
                if(list.size() == 0) stack.peek().pop();
                stack.pop();
                next.accept(span.skip(1));
            }
            else mismatch.call();
        };
    }
    static SyntaxStackRule popRule(Text symbol)
    {
        return (span, stack, next, mismatch) ->
        {
            if(span.at(0).text().compare(symbol))
            {
                stack.pop();
                next.accept(span.skip(1));
            }
            else mismatch.call();
        };
    }
    static SyntaxStackRule commaRule()
    {
        return (span, stack, next, mismatch) ->
        {
            if(span.at(0).text().compareString(","))
            {
                var nl = SyntaxList.invocationList();
                stack.pop();
                stack.peek().push(nl);
                stack.push(nl);
                next.accept(span.skip(1));
            }
            else mismatch.call();
        };
    }
    static SyntaxStackRule addSyntaxRule(SyntaxRule rule)
    {
        return (span, stack, next, mismatch) ->
        {
            rule.match(span, (syntax, nextSpan) ->
            {
                stack.peek().push(SyntaxUnit.of(syntax));
                next.accept(nextSpan);
            },
            mismatch);
        };
    }
    static SyntaxStackRule arrowRule()
    {
        return (span, stack, next, mismatch) ->
        {
            stack.peek().arrowRule().match(span, stack, next, mismatch);
        };
    }
    static SyntaxStackRule arrowRuleCommon()
    {
        return splitRule(Text.of("->"), SyntaxList::arrowList);
    }
    static SyntaxStackRule arrowRuleAssignment()
    {
        return (span, stack, next, mismatch) ->
        {
            if(span.at(0).text().compareString("->"))
            {
                var nl = SyntaxList.invocationList();
                stack.peek().push(nl);
                stack.push(nl);
                next.accept(span.skip(1));
            }
            else mismatch.call();
        };
    }
    static SyntaxStackRule splitRule(Text symbol, Supplier<SyntaxList> newList)
    {
        return (span, stack, next, mismatch) ->
        {
            if(stack.peek().size() != 0 && span.at(0).text().compare(symbol))
            {
                var first = stack.peek().pop();
                var nl = newList.get();
                nl.push(first);
                stack.peek().push(nl);
                stack.push(nl);
                next.accept(span.skip(1));
            }
            else mismatch.call();
        };
    }
    static SyntaxStackRule assignmentRule()
    {
        return (span, stack, next, mismatch) ->
        {
            if(stack.peek().size() != 0 && span.at(0).text().compareString("="))
            {
                var first = stack.peek().pop();
                var nl = SyntaxList.assignmentList();
                nl.push(first);
                stack.peek().push(nl);
                stack.push(nl);
                next.accept(span.skip(1));
            }
            else mismatch.call();
        };
    }
    static SyntaxStackRule of(SyntaxStackRule... rules)
    {
        return (span, stack, next, mismatch) ->
        {
            boolean[] hasMatch = { false };
            int index = 0;
            do
            {
                rules[index++].match(span, stack, nextSpan ->
                {
                    hasMatch[0] = true;
                    next.accept(nextSpan);
                },
                () -> { });
            }
            while(!hasMatch[0] && index < rules.length);
            if(!hasMatch[0])
            {
                mismatch.call();
            }
        };
    }

    static void matchStack(SyntaxListStack stack, SourceSpan span, Action mismatch)
    {
        if(span.at(0).isEmpty()) return;
        stackRule.match(span, stack, nextSpan -> matchStack(stack, nextSpan, mismatch), mismatch);
    }
}