package jena.lang.syntax;

import java.util.function.BinaryOperator;
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
        Consumer<Syntax> add,
        Consumer<SyntaxList> push,
        Supplier<SyntaxList> pop,
        Supplier<SyntaxList> peek,
        Consumer<SourceSpan> next,
        Action mismatch);

    static final SyntaxStackRule identity = (span, add, push, pop, peek, next, mismatch) -> { };
    static final SyntaxStackRule pop = (span, add, push, pop, peek, next, mismatch) -> pop.get();

    static SyntaxStackRule pushRule(Text symbol, Supplier<SyntaxList> list)
    {
        return (span, add, push, pop, peek, next, mismatch) ->
        {
            if(span.at(0).text().compare(symbol))
            {
                push.accept(list.get());
                next.accept(span.skip(1));
            }
            else mismatch.call();
        };
    }
    static SyntaxStackRule pushNestedRule(Text symbol, Supplier<SyntaxList> outer, Supplier<SyntaxList> inner)
    {
        return (span, add, push, pop, peek, next, mismatch) ->
        {
            if(span.at(0).text().compare(symbol))
            {
                push.accept(outer.get());
                push.accept(inner.get());
                next.accept(span.skip(1));
            }
            else mismatch.call();
        };
    }
    static SyntaxStackRule popNestedRule(Text symbol)
    {
        return (span, add, push, pop, peek, next, mismatch) ->
        {
            if(span.at(0).text().compare(symbol))
            {
                pop.get();
                pop.get();
                next.accept(span.skip(1));
            }
            else mismatch.call();
        };
    }
    static SyntaxStackRule popRule(Text symbol)
    {
        return (span, add, push, pop, peek, next, mismatch) ->
        {
            if(span.at(0).text().compare(symbol))
            {
                pop.get();
                next.accept(span.skip(1));
            }
            else mismatch.call();
        };
    }
    static SyntaxStackRule resetRule(Text symbol)
    {
        return (span, add, push, pop, peek, next, mismatch) ->
        {
            if(span.at(0).text().compare(symbol))
            {
                push.accept(pop.get().fresh());
                next.accept(span.skip(1));
            }
            else mismatch.call();
        };
    }
    static SyntaxStackRule addSyntaxRule(SyntaxRule rule)
    {
        return (span, add, push, pop, peek, next, mismatch) ->
        {
            rule.match(span, (syntax, nextSpan) ->
            {
                add.accept(syntax);
                next.accept(nextSpan);
            },
            mismatch);
        };
    }
    static SyntaxStackRule arrowRule(SyntaxRule left, Text binding, BinaryOperator<Syntax> function)
    {
        return (span, add, push, pop, peek, next, mismatch) ->
        {
            left.match(span, (syntax, nextSpan) ->
            {
                if(nextSpan.at(0).text().compare(binding))
                {
                    push.accept(SyntaxList.bindingList(function, p -> p.get().add(() -> new NoneValueSyntax())));
                    add.accept(syntax);
                    next.accept(nextSpan.skip(1));
                }
                else mismatch.call();
            },
            mismatch);
        };
    }
    static SyntaxStackRule of(GenericList<SyntaxStackRule> rules)
    {
        return (span, add, push, pop, peek, next, mismatch) ->
        {
            rules.read((f, r) ->
            {
                f.match(span, add, push, pop, peek, next, () ->
                {
                    of(r).match(span, add, push, pop, peek, next, mismatch);
                });
            },
            mismatch);
        };
    }
}