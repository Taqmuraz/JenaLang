package jena.lang;

public final class EmptySourceCheck
{
    private boolean empty = true;

    public EmptySourceCheck(Source source)
    {
        source.read(StartPosition.instance, MaxCount.instance, (c, n) -> empty = false);
    }

    public boolean isEmpty()
    {
        return empty;
    }
}