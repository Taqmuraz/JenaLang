package jena.lang;

public final class ArrayBuffer<Element> implements GenericBuffer<Element>
{
    private Element[] elements;

    @SafeVarargs
    public ArrayBuffer(Element... elements)
    {
        this.elements = elements;
    }

    @Override
    public int length()
    {
        return elements.length;
    }

    @Override
    public Element at(int index)
    {
        return elements[index];
    }
}