package jena.lang;

public final class StructPair<A, B> implements GenericPair<A, B>
{
    public A a;
    public B b;

    public StructPair(A a, B b)
    {
        this.a = a;
        this.b = b;
    }
    public StructPair(GenericPair<A, B> pair)
    {
        pair.both((a, b) ->
        {
            this.a = a;
            this.b = b;
        });
    }

    @Override
    public void both(GenericPairBothAction<A, B> action)
    {
        action.call(a, b);
    }
}