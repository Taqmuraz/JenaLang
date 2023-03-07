package jena.lang;

public final class SingleGenericFlow<Element> implements GenericFlow<Element>
{
    private Element element;
    
    public SingleGenericFlow(Element element)
    {
        this.element = element;
    }

    @Override
    public void read(GenericArrayElementAction<Element> action)
    {
        action.call(element, 0);
    }

    @Override
    public GenericBuffer<Element> collect()
    {
        return new GenericBuffer<Element>()
        {
            @Override
            public int length()
            {
                return 1;
            }

            @Override
            public Element at(int index)
            {
                return element;
            }
        };
    }
}