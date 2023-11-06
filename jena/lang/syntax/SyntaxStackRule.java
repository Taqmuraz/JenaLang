package jena.lang.syntax;

import java.util.function.Consumer;
import java.util.function.Supplier;

import jena.lang.Action;
import jena.lang.GenericList;
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

    static final SyntaxStackRule stackRule = of(GenericList.of(
        arrowRule(Text.of("->")),
        //SyntaxStackRule.bindingRule(new NameExpressionSyntaxRule(), Text.of("="), AssignmentExpressionSyntax::new),
        addSyntaxRule(syntaxRule),
        pushRule(Text.of('('), SyntaxList::invocationList),
        pushNestedRule(Text.of('['), SyntaxList::arrayList, SyntaxList::invocationList),
        pushNestedRule(Text.of('{'), SyntaxList::mapList, SyntaxList::invocationList),
        popRule(Text.of(')')),
        popNestedRule(Text.of(']')),
        popNestedRule(Text.of('}')),
        commaRule(Text.of(','))
    ));

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
    static SyntaxStackRule commaRule(Text symbol)
    {
        return (span, stack, next, mismatch) ->
        {
            if(span.at(0).text().compare(symbol))
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
    static SyntaxStackRule arrowRule(Text symbol)
    {
        return (span, stack, next, mismatch) ->
        {
            if(stack.peek().size() != 0 && span.at(0).text().compare(symbol))
            { 
                var first = stack.peek().pop();
                var nl = SyntaxList.arrowList();
                nl.push(first);
                stack.peek().push(nl);
                stack.push(nl);
                stack.postponePop();
                next.accept(span.skip(1));
            }
            else mismatch.call();
        };
    }
    static SyntaxStackRule of(GenericList<SyntaxStackRule> rules)
    {
        return (span, stack, next, mismatch) ->
        {
            rules.read((f, r) ->
            {
                f.match(span, stack, next, () ->
                {
                    of(r).match(span, stack, next, mismatch);
                });
            },
            mismatch);
        };
    }

    static void matchStack(SyntaxListStack stack, SourceSpan span, Action mismatch)
    {
        if(span.at(0).isEmpty()) return;
        stackRule.match(span, stack, nextSpan -> matchStack(stack, nextSpan, mismatch), mismatch);
    }
}