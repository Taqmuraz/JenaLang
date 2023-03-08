package jena.lang;

public final class SingleGenericFlow<Element> implements GenericFlow<Element>
{
    private Element element;
    
    public SingleGenericFlow(Element element)
    {
        this.element = element;
    }

    @Override
    public void read(GenericAction<Element> action)
    {
        action.call(element);
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