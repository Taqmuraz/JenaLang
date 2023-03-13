package jena.lang;

import java.util.List;

public final class ListBuffer<Element> implements GenericBuffer<Element>
{
    private List<Element> list;

    public ListBuffer(List<Element> list)
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