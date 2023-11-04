package jena.lang;

public interface GenericFunctionTwo<Arg1, Arg2, Result>
{
    Result call(Arg1 arg1, Arg2 arg2);
}