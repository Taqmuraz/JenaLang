package jena.lang;

public interface Condition<T>
{
    boolean match(T item);
}