package jena.lang;

import java.util.ArrayList;
import java.util.function.Function;

public interface Result<Item, None>
{
    void ifPresentElse(GenericAction<Item> result, GenericAction<None> none);
    
    default <Throws extends RuntimeException> Item itemOrThrow(Function<None, Throws> errorFunction)
    {
        ArrayList<Item> item = new ArrayList<>();
        ifPresentElse(i -> item.add(i), none ->
        {
            throw errorFunction.apply(none);
        });
        return item.get(0);
    }

    default <Out>Result<Out, None> map(GenericFunction<Item, Result<Out, None>> function)
    {
        return (result, none) ->
        {
            ifPresentElse(item -> function.call(item).ifPresentElse(result, none), none);
        };
    }
    default Result<Item, None> orElse(Item item)
    {
        return (result, none) ->
        {
            ifPresentElse(result, n -> result.call(item));
        };
    }

    static<Item, None> Result<Item, None> result(Item item)
    {
        return (result, none) -> result.call(item);
    }
    static<Item, None> Result<Item, None> none(None n)
    {
        return (result, none) -> none.call(n);
    }
}