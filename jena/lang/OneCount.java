package jena.lang;

public final class OneCount implements Count
{
    public static final Count instance = new OneCount();

    @Override
    public int count(int max)
    {
        return Math.min(max, 1);
    }
}