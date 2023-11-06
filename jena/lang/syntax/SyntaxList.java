package jena.lang.syntax;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import jena.lang.GenericBuffer;
import jena.lang.GenericList;

public interface SyntaxList
{
    void add(Supplier<Syntax> syntax);
    Syntax complete();
    SyntaxList fresh();

    static SyntaxList of(Function<GenericList<Syntax>, Syntax> complete)
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
}