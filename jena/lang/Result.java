package jena.lang;

import java.util.function.Function;

public class Result<Item, None>
{
    Item result;
    None none;
    boolean hasResult;

    private Result(Item result, None none, boolean hasResult)
    {
        this.result = result;
        this.none = none;
        this.hasResult = hasResult;
    }

    public <Throws extends RuntimeException> Item itemOrThrow(Function<None, Throws> errorFunction)
    {
        if(!hasResult)
        {
            throw errorFunction.apply(none);
        }
        return result;
    }

    public <Out>Result<Out, None> map(GenericFunction<Item, Result<Out, None>> function)
    {
        if(hasResult) return function.call(result);
        else return Result.none(none);
    }
    public Result<Item, None> orElse(Item item)
    {
        if(hasResult) return this;
        else return Result.result(item);
    }

    public static<Item, None> Result<Item, None> result(Item item)
    {
        return new Result<>(item, null, true);
    }
    public static<Item, None> Result<Item, None> none(None none)
    {
        return new Result<>(null, none, false);
    }
}