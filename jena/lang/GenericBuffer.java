package jena.lang;

import java.util.function.Function;

public interface GenericBuffer<Element>
{
    int length();
    Element at(int index);

    static GenericBuffer<Integer> range(int length)
    {
        return new GenericBuffer<Integer>()
        {
            @Override
            public int length()
            {
                return length;
            }
            @Override
            public Integer at(int index)
            {
                return index;
            }
        };
    }

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
    default<Other, Zip> GenericBuffer<Zip> zip(GenericBuffer<Other> other, GenericFunctionTwo<Element, Other, Zip> function)
    {
        int length = Math.min(length(), other.length());
        return range(length).map(i -> function.call(at(i), other.at(i)));
    }
    default boolean all(Condition<Element> condition)
    {
        for(int i = 0, length = length(); i < length; i++)
        {
            if(!condition.match(at(i))) return false;
        }
        return true;
    }
    default Element[] toArray(Function<Integer, Element[]> factory)
    {
        Element[] array = factory.apply(length());
        for(int i = 0; i < array.length; i++)
        {
            array[i] = at(i);
        }
        return array;
    }
}