package jena.lang;

import java.util.ArrayList;
import java.util.List;

public interface GenericFlow<Element>
{
    void read(GenericAction<Element> action);

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
        read(e -> list.add(e));
        return new ListGenericBuffer<Element>(list);
    }
    default <Other> GenericFlow<GenericPair<Element, Other>> connect(GenericFunction<Element, Other> connection)
    {
        return new MapFlow<Element, GenericPair<Element, Other>>(this, e -> p -> p.call(e, connection.call(e)));
    }
    default <Other> GenericFlow<GenericPair<Element, Other>> zip(GenericFlow<Other> other)
    {
        return action ->
        {
            GenericBuffer<Element> es = collect();
            GenericBuffer<Other> os = other.collect();
            int length = Integer.min(es.length(), os.length());
            for(int i = 0; i < length; i++)
            {
                int index = i;
                action.call(both -> both.call(es.at(index), os.at(index)));
            }
        };
    }
    default GenericFlow<Element> append(Element element)
    {
        return action ->
        {
            read(action);
            action.call(element);
        };
    }
}