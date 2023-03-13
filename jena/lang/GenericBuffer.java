package jena.lang;

public interface GenericBuffer<Element>
{
    int length();
    Element at(int index);

    default GenericFlow<Element> flow()
    {
        return action ->
        {
            for(int i = 0; i < length(); i++)
            {
                action.call(at(i));
            }
        };
    }

    default<Out> GenericBuffer<Out> map(GenericFunction<Element, Out> map)
    {
        return new MapBuffer<Element, Out>(this, map);
    }
    default GenericBuffer<Element> join(Element element)
    {
        return new JoinGenericBuffer<Element>(this, element);
    }
    default void each(GenericAction<Element> action)
    {
        int length = length();
        for(int i = 0; i < length; i++) action.call(at(i));
    }
    default<Other> GenericBuffer<GenericPair<Element, Other>> zip(GenericBuffer<Other> others)
    {
        return new ZipBuffer<Element, Other>(this, others);
    }
}