package jena.lang;

import java.util.ArrayList;
import java.util.List;

public interface GenericFlow<Element>
{
    void read(GenericArrayElementAction<Element> action);

    default <Out> GenericFlow<Out> map(GenericFunction<Element, Out> map)
    {
        return new MapFlow<Element, Out>(this, map);
    }
    default void join(GenericAction<Element> action, Action separator)
    {
        new JoinAction<Element>(this, action, separator).join();
    }
    default GenericBuffer<Element> collect()
    {
        List<Element> list = new ArrayList<Element>();
        read((e, i) -> list.add(e));
        return new ListGenericBuffer<Element>(list);
    }
}