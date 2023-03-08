package jena.lang;

import java.util.Objects;

public final class EmptyGenericBuffer<Element> implements GenericBuffer<Element>
{
    public EmptyGenericBuffer()
    {
    }

    @Override
    public int length()
    {
        return 0;
    }

    @Override
    public Element at(int index)
    {
        Objects.checkIndex(index, 0);
        return null;
    }
}