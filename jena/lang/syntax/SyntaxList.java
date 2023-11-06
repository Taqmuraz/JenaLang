package jena.lang.syntax;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import jena.lang.GenericBuffer;
import jena.lang.GenericList;

public interface SyntaxList
{
    void add(Supplier<Syntax> syntax);
    Syntax complete();
    SyntaxList fresh();
    void close(Supplier<SyntaxList> pop);

    static SyntaxList of(Function<GenericList<Syntax>, Syntax> complete)
    {
        return of(complete, p -> { });
    }

    static SyntaxList of(Function<GenericList<Syntax>, Syntax> complete, Consumer<Supplier<SyntaxList>> close)
    {
        List<Supplier<Syntax>> list = new ArrayList<>();
        return new SyntaxList()
        {
            @Override
            public void add(Supplier<Syntax> syntax)
            {
                list.add(syntax);
            }
            @Override
            public Syntax complete()
            {
                return complete.apply(GenericList.of(list.stream().map(l -> l.get()).toList()));
            }
            @Override
            public SyntaxList fresh()
            {
                return invocationList();
            }
            @Override
            public void close(Supplier<SyntaxList> pop)
            {
                close.accept(pop);
            }
        };
    }
    static Function<GenericList<Syntax>, Syntax> completeInvocationList()
    {
        return list ->
        {
            Syntax[] result = { new NoneValueSyntax() };
            list.read((first, rest) ->
            {
                result[0] = first;
                rest.iterate(f ->
                {
                    result[0] = new InvocationExpressionSyntax(result[0], f);
                });
            });
            return result[0];
        };
    }
    static Function<GenericList<Syntax>, Syntax> completeArrayList()
    {
        return list ->
        {
            return ExpressionListSyntax.arraySyntax(GenericBuffer.of(list.collect()));
        };
    }
    static Function<GenericList<Syntax>, Syntax> completeMapList()
    {
        return list ->
        {
            return ExpressionListSyntax.mapSyntax(GenericBuffer.of(list.collect()));
        };
    }
    static Function<GenericList<Syntax>, Syntax> completeBindingList(BinaryOperator<Syntax> function)
    {
        return list ->
        {
            Syntax[] result = { new NoneValueSyntax() };
            list.read((left, rest) ->
            {
                var right = completeInvocationList().apply(rest);
                result[0] = function.apply(left, right);
            });
            return result[0];
        };
    }
    static SyntaxList invocationList()
    {
        return of(completeInvocationList());
    }
    static SyntaxList arrayList()
    {
        return of(completeArrayList());
    }
    static SyntaxList mapList()
    {
        return of(completeMapList());
    }
    static SyntaxList bindingList(BinaryOperator<Syntax> function, Consumer<Supplier<SyntaxList>> close)
    {
        return of(completeBindingList(function), close);
    }
}