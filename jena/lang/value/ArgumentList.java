package jena.lang.value;

public interface ArgumentList
{
    Value number(int number, ValueListFunction action, ValueFunction fail);
}