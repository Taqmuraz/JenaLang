package jena.lang;

public interface GenericPair<A, B>
{
    void both(GenericPairBothAction<A, B> action);
}