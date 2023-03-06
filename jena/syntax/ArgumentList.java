package jena.syntax;

public interface ArgumentList
{
    Value number(int number, ValueListFunction action, ValueFunction fail);
}