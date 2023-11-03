package jena.lang;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public interface GenericList<Element>
{
    public interface FirstRestAction<Element>
    {
        void call(Element first, GenericList<Element> rest);
    }
    void read(FirstRestAction<Element> hasElement, Action noElement);
    default void read(FirstRestAction<Element> action)
    {
        read(action, () -> { });
    }

    static <Element> GenericList<Element> single(Element item)
    {
        return (action, empty) -> action.call(item, empty());
    }
    static <Element> GenericList<Element> empty()
    {
        return (action, empty) -> empty.call();
    }
    static <Element> GenericList<Element> of(Iterator<Element> it)
    {
        return (action, empty) ->
        {
            if(it.hasNext()) action.call(it.next(), of(it));
            else empty.call();
        };
    }
    static <Element> GenericList<Element> of(Iterable<Element> coll)
    {
        return of(coll.iterator());
    }
    default Iterable<Element> iterable()
    {
        GenericList<Element> start = this;
        return () -> new Iterator<Element>()
        {
            Element next;
            GenericList<Element> rest = start;
            boolean hasNext;
            @Override
            public boolean hasNext()
            {
                hasNext = false;
                rest.read((f, r) ->
                {
                    next = f;
                    rest = r;
                    hasNext = true;
                });
                return hasNext;
            }

            @Override
            public Element next()
            {
                return next;
            }
        };
    }
    default GenericList<Element> prepend(Element item)
    {
        return (action, empty) -> action.call(item, this);
    }
    default GenericList<Element> append(Element item)
    {
        return (action, empty) -> read((f, r) -> action.call(f, r.append(item)), () -> action.call(item, empty()));
    }
    default GenericList<Element> reverse()
    {
        return (action, empty) -> read((f, r) -> r.reverse().append(f).read(action, empty), empty);
    }
    static <Element> FirstRestAction<Element> iterateAction(GenericAction<Element> action)
    {
        return (f, r) ->
        {
            action.call(f);
            r.read(iterateAction(action));
        };
    }
    default void iterate(GenericAction<Element> action)
    {
        read(iterateAction(action));
    }
    default List<Element> collect()
    {
        List<Element> l = new ArrayList<Element>();
        iterate(l::add);
        return l;
    }
}