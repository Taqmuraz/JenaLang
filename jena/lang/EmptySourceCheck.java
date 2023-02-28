package jena.lang;

public final class EmptySourceCheck
{
    private boolean empty = true;

    public EmptySourceCheck(Source source)
    {
        source.read(c -> empty = false);
    }

    public boolean isEmpty()
    {
        return empty;
    }
}