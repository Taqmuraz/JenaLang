package jena.lang;

public interface Optional<Item>
{
    void ifPresent(GenericAction<Item> action);
    void ifPresentElse(GenericAction<Item> present, Action notPresent);
    <Out>Optional<Out> mapIfPresent(GenericFunction<Item, Out> function);
    <Out>Optional<Out> mapIfPresentElse(GenericFunction<Item, Out> present, GenericProducer<Out> notPresent);
}