package jena.lang;

public class Optional<Item>
{
    boolean present;
    Item item;

    private Optional(Item item)
    {
        this.item = item;
        this.present = true;
    }
    private Optional()
    {
        this.present = false;
    }

    public boolean present()
    {
        return present;
    }
    public Item item()
    {
        if(!present) throw new RuntimeException("Item is not present");
        return item;
    }

    public <Out>Optional<Out> map(GenericFunction<Item, Out> function)
    {
        if(present) return Optional.yes(function.call(item));
        else return Optional.no();
    }
    public <Out>Optional<Out> mapOptional(GenericFunction<Item, Optional<Out>> function)
    {
        if(present) return function.call(item);
        return Optional.no();
    }
    public Optional<Item> orElse(Item item)
    {
        if(present) return this;
        else return Optional.yes(item);
    }

    public static<Item> Optional<Item> yes(Item item)
    {
        return new Optional<>(item);
    }
    public static<Item> Optional<Item> no()
    {
        return new Optional<>();
    }
}