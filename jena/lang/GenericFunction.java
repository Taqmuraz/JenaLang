package jena.lang;

public interface GenericFunction<Arg, Result>
{
    Result call(Arg arg);
}