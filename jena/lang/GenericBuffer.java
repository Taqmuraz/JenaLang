package jena.lang;

public interface GenericBuffer<Element>
{
    int length();
    Element at(int index);

    default GenericFlow<Element> flow()
    {
        return action ->
        {
            for(int i = 0; i < length(); i++)
            {
                action.call(at(i));
            }
        };
    }

    default<Out> GenericBuffer<Out> map(GenericFunction<Element, Out> map)
    {
        return new MapBuffer<Element, Out>(this, map);
    }
}