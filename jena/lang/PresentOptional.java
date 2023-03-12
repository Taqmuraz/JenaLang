package jena.lang;

public class PresentOptional<Item> implements Optional<Item>
{
    private Item item;

    public PresentOptional(Item item)
    {
        this.item = item;
    }

    @Override
    public void ifPresent(GenericAction<Item> action)
    {
        action.call(item);
    }

    @Override
    public void ifPresentElse(GenericAction<Item> present, Action notPresent)
    {
        present.call(item);
    }

    @Override
    public <Out> Optional<Out> mapIfPresent(GenericFunction<Item, Out> function)
    {
        return new PresentOptional<Out>(function.call(item));
    }

    @Override
    public <Out> Optional<Out> mapIfPresentElse(GenericFunction<Item, Out> present, GenericProducer<Out> notPresent)
    {
        return new PresentOptional<Out>(present.call(item));
    }
}