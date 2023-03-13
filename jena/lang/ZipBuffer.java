package jena.lang;

public final class ZipBuffer<Element, Other> implements GenericBuffer<GenericPair<Element, Other>>
{
    private GenericBuffer<Element> elements;
    private GenericBuffer<Other> others;

    public ZipBuffer(GenericBuffer<Element> elements, GenericBuffer<Other> others)
    {
        this.elements = elements;
        this.others = others;
    }

    @Override
    public int length()
    {
        return Integer.min(elements.length(), others.length());
    }

    @Override
    public GenericPair<Element, Other> at(int index)
    {
        return new StructPair<Element, Other>(elements.at(index), others.at(index));
    }
}