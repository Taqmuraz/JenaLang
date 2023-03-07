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
                action.call(at(i), i);
            }
        };
    }
}