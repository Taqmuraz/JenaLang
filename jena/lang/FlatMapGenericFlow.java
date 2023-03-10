package jena.lang;

public final class FlatMapGenericFlow<Element, Out> implements GenericFlow<Out>
{
    private GenericFlow<Element> flow;
    private GenericFunction<Element, GenericFlow<Out>> mapping;

    public FlatMapGenericFlow(GenericFlow<Element> flow, GenericFunction<Element, GenericFlow<Out>> mapping)
    {
        this.flow = flow;
        this.mapping = mapping;
    }

    @Override
    public void read(GenericAction<Out> reader)
    {
        flow.read(element -> mapping.call(element).read(reader));
    }
}