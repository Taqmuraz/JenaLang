package jena.lang;

public final class MapGenericBuffer<In, Out> implements GenericBuffer<Out>
{
    private GenericBuffer<In> buffer;
    private GenericFunction<In, Out> map;

    public MapGenericBuffer(GenericBuffer<In> buffer, GenericFunction<In, Out> map)
    {
        this.buffer = buffer;
        this.map = map;
    }

    @Override
    public int length()
    {
        return buffer.length();
    }

    @Override
    public Out at(int index)
    {
        return map.call(buffer.at(index));
    }
}