package jena.lang;

public class IterableFlow<T> implements GenericFlow<T>
{
    Iterable<T> iterable;

    public IterableFlow(Iterable<T> iterable)
    {
        this.iterable = iterable;
    }

    @Override
    public void read(GenericAction<T> action)
    {
        for(T item : iterable) action.call(item);
    }
}