package jena.lang;

public interface GenericAction<Arg>
{
    void call(Arg arg);
}