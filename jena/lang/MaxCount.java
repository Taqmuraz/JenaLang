package jena.lang;

public final class MaxCount implements Count
{
    public static final MaxCount instance = new MaxCount();

    @Override
    public int count(int max)
    {
        return max;
    }
}