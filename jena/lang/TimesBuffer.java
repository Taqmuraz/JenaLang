package jena.lang;

public final class TimesBuffer implements GenericBuffer<Integer>
{
    private int times;

    public TimesBuffer(int times)
    {
        this.times = times;
    }

    @Override
    public int length()
    {
        return times;
    }

    @Override
    public Integer at(int index)
    {
        return Integer.valueOf(index);
    }
}