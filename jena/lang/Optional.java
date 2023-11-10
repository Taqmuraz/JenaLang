package jena.lang;

import java.util.ArrayList;
import java.util.List;

public interface Optional<Item>
{
    void ifPresentElse(GenericAction<Item> present, Action notPresent);
    default void ifPresent(GenericAction<Item> action)
    {
        ifPresentElse(action, () -> { });
    }
    default <Out>Optional<Out> map(GenericFunction<Item, Out> function)
    {
        return (present, notPresent) ->
        {
            ifPresentElse(item -> present.call(function.call(item)), notPresent);
        };
    }
    default <Out>Optional<Out> mapOptional(GenericFunction<Item, Optional<Out>> function)
    {
        return (present, notPresent) ->
        {
            ifPresentElse(item -> function.call(item).ifPresentElse(present, notPresent), notPresent);
        };
    }
    default Optional<Item> orElse(Item item)
    {
        return (present, notPresent) ->
        {
            ifPresentElse(present, () -> present.call(item));
        };
    }
    default Item itemOrDefault(Item defaultItem)
    {
        List<Item> item = new ArrayList<Item>();
        ifPresent(item::add);
        return item.isEmpty() ? defaultItem : item.get(0);
    }

    static<Item> Optional<Item> yes(Item item)
    {
        return (present, notPresent) -> present.call(item);
    }
    static<Item> Optional<Item> no()
    {
        return (present, notPresent) -> notPresent.call();
    }
}