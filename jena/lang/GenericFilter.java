package jena.lang;

public interface GenericFilter<Element>
{
    boolean pass(Element element);
}