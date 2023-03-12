package jena.lang;

public class NotPresentOptional<Item> implements Optional<Item>
{
    @Override
    public void ifPresent(GenericAction<Item> action)
    {
    }

    @Override
    public void ifPresentElse(GenericAction<Item> present, Action notPresent)
    {
        notPresent.call();
    }

    @Override
    public <Out> Optional<Out> mapIfPresent(GenericFunction<Item, Out> function)
    {
        return new NotPresentOptional<Out>();
    }

    @Override
    public <Out> Optional<Out> mapIfPresentElse(GenericFunction<Item, Out> present, GenericProducer<Out> notPresent)
    {
        return new PresentOptional<Out>(notPresent.item());
    }
}