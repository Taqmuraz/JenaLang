package jena.lang;

public final class MinCount implements Count
{
    private int count;

    public MinCount(int count)
    {
        this.count = count;
    }

    @Override
    public int count(int max)
    {
        return Math.min(max, count);
    }
}