package jena.lang;

public class MutableOptional<Item> implements Optional<Item> 
{
    private boolean hasItem;
    private Item item;

    public void apply(Item item)
    {
        this.item = item;
        hasItem = true;
    }

    @Override
    public void ifPresent(GenericAction<Item> action)
    {
        if(hasItem)action.call(item);
    }

    @Override
    public void ifPresentElse(GenericAction<Item> present, Action notPresent)
    {
        if(hasItem)present.call(item);
        else notPresent.call();
    }

    @Override
    public <Out> Optional<Out> mapIfPresent(GenericFunction<Item, Out> function)
    {
        if(hasItem) return new PresentOptional<Out>(function.call(item));
        return new NotPresentOptional<Out>();
    }

    @Override
    public <Out> Optional<Out> mapIfPresentElse(GenericFunction<Item, Out> present, GenericProducer<Out> notPresent)
    {
        if(hasItem) return new PresentOptional<Out>(present.call(item));
        return new PresentOptional<Out>(notPresent.item());
    }
}