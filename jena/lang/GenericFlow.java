package jena.lang;

public interface GenericFlow<Element>
{
    void read(GenericArrayElementAction<Element> action);
}