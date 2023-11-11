package jena.lang;

public interface GenericFunction<Arg, Value>
{
    Value call(Arg arg);
}