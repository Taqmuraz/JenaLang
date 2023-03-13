package jena.lang;

public final class JoinGenericBuffer<Element> implements GenericBuffer<Element>
{
    private GenericBuffer<Element> source;
    private Element separator;

    public JoinGenericBuffer(GenericBuffer<Element> source, Element separator)
    {
        this.source = source;
        this.separator = separator;
    }

    @Override
    public int length()
    {
        return (source.length() * 2) - 1;
    }

    @Override
    public Element at(int index)
    {
        if((index & 1) == 0) return source.at(index >> 1);
        return separator;
    }
}