package jena.lang;

public final class ArrayGenericBuffer<Element> implements GenericBuffer<Element>
{
    private Element[] elements;

    public ArrayGenericBuffer(Element[] elements)
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