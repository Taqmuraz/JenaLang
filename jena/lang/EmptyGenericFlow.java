package jena.lang;

public final class EmptyGenericFlow<Element> implements GenericFlow<Element>
{
    @Override
    public void read(GenericAction<Element> action)
    {
    }
}