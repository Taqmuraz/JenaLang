package jena.lang.value;

public final class ArrayValueList implements ValueList
{
    private Value[] values;

    public ArrayValueList(Value... values)
    {
        this.values = values;
    }

    @Override
    public Value at(int index)
    {
        return values[index];
    }
}