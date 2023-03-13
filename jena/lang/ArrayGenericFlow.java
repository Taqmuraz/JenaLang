package jena.lang;

public final class ArrayGenericFlow<Element> implements GenericFlow<Element>
{
    private Element[] elements;

    @SafeVarargs
    public ArrayGenericFlow(Element... elements)
    {
        this.elements = elements;
    }

    @Override
    public void read(GenericAction<Element> action)
    {
        for(int i = 0; i < elements.length; i++) action.call(elements[i]);
    }
}