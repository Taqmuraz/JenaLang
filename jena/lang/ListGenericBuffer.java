package jena.lang;

import java.util.List;

public final class ListGenericBuffer<Element> implements GenericBuffer<Element>
{
    private List<Element> list;

    public ListGenericBuffer(List<Element> list)
    {
        this.list = list;
    }

    @Override
    public int length()
    {
        return list.size();
    }

    @Override
    public Element at(int index)
    {
        return list.get(index);
    }
}