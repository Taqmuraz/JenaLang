package jena.lang;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

public interface GenericList<Element>
{
    public interface FirstRestAction<Element>
    {
        void call(Element first, GenericList<Element> rest);
    }
    void read(FirstRestAction<Element> action);

    static <Element> GenericList<Element> single(Element item)
    {
        return action -> action.call(item, empty());
    }
    static <Element> GenericList<Element> empty()
    {
        return action -> { };
    }
    static <Element> GenericList<Element> of(Iterator<Element> it)
    {
        return action ->
        {
            if(it.hasNext()) action.call(it.next(), of(it));
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
    default GenericList<Element> append(Element item)
    {
        return action -> action.call(item, this);
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
    default GenericList<Element> reverse()
    {
        List<Element> s = new ArrayList<Element>();
        iterate(s::add);
        return of(IntStream.range(0, s.size()).boxed().map(i -> s.get(s.size() - 1 - i)).toList());
    }
}