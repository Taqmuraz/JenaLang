package jena.lang.value;

public final class EmptyValueList implements ValueList
{
    public final static ValueList instance = new EmptyValueList(); 

    @Override
    public Value at(int index)
    {
        return NoneValue.instance;
    }
}