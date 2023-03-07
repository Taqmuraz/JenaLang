package jena.lang;

public interface GenericBuffer<Element>
{
    int length();
    Element at(int index);
}