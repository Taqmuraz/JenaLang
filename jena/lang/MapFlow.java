package jena.lang;

public final class MapFlow<In, Out> implements GenericFlow<Out>
{
    private GenericFlow<In> flow;
    private GenericFunction<In, Out> map;
    
    public MapFlow(GenericFlow<In> flow, GenericFunction<In, Out> map)
    {
        this.flow = flow;
        this.map = map;
    }

    @Override
    public void read(GenericArrayElementAction<Out> action)
    {
        flow.read((element, index) -> action.call(map.call(element), index));
    }
}