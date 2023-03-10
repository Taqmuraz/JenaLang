package jena.lang;

public interface GenericPair<A, B>
{
    void both(GenericPairBothAction<A, B> action);

    default StructPair<A, B> struct()
    {
        return new StructPair<A, B>(this);
    }
}