package jena.lang;

public interface GenericFunctionTwo<Arg1, Arg2, Value>
{
    Value call(Arg1 arg1, Arg2 arg2);
}