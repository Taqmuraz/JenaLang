package jena.lang;

import java.util.List;

public final class ListGenericFlow<Element> implements GenericFlow<Element>
{
    private List<Element> elements;

    public ListGenericFlow(List<Element> elements)
    {
        this.elements = elements;
    }

    @Override
    public void read(GenericAction<Element> action)
    {
        for(int i = 0; i < elements.size(); i++) action.call(elements.get(i));
    }
}