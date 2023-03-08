package jena.lang;

import java.util.Objects;

public final class SingleGenericBuffer<Element> implements GenericBuffer<Element>
{
    private Element element;

    public SingleGenericBuffer(Element element)
    {
        this.element = element;
    }

    @Override
    public int length()
    {
        return 1;
    }

    @Override
    public Element at(int index)
    {
        Objects.checkIndex(index, 1);
        return element;
    }
}