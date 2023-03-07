package jena.lang;

public interface GenericArrayElementAction<Element>
{
    void call(Element element, int index);
}