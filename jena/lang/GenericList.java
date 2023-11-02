package jena.lang;

public interface GenericList<Element>
{
    public interface GenericListElementAction<Element>
    {
        void call(Element head, GenericList<Element> tail);
    }
    void read(GenericListElementAction<Element> action);

    static <Element> GenericList<Element> empty()
    {
        return action -> { };
    }
    static <Element> GenericList<Element> single(Element item)
    {
        return action -> action.call(item, empty());
    }
    static <Element> GenericList<Element> headTail(Element head, GenericList<Element> tail)
    {
        return action -> action.call(head, tail);
    }
    static <Element> GenericList<Element> tailHead(GenericList<Element> tail, Element head)
    {
        
    }

    default void readOrElse(GenericList<Element> e, GenericListElementAction<Element> action)
    {
        boolean[] hasElements = { false };
        read((h, t) ->
        {
            hasElements[0] = true;
            action.call(h, t);
        });
        if(!hasElements[0]) e.read(action);
    }

    default GenericList<Element> append(Element item)
    {
        return headTail(item, this);
    }
    default GenericList<Element> prepend(Element item)
    {
        return tailHead(this, item);
    }
    default GenericList<Element> reverse()
    {
        return action -> read((h, t) -> tailHead(t, h).read(action));
    }
}