package jena.lang;

public final class EmptyGenericBuffer<Element> implements GenericBuffer<Element>
{
    private Element element;

    public EmptyGenericBuffer(Element defaultElement)
    {
        this.element = defaultElement;
    }

    @Override
    public int length()
    {
        return 0;
    }

    @Override
    public Element at(int index)
    {
        return element;
    }
}