package jena.lang.value;

import jena.lang.text.TextWriter;

public final class SingleElementMapValue implements Value
{
    private final Value element;
    private final Value value;

    public SingleElementMapValue(Value element, Value value)
    {
        this.element = element;
        this.value = value;
    }
    @Override
    public void print(TextWriter writer)
    {
        writer.write("{");
        element.print(writer);
        writer.write(":");
        value.print(writer);
        writer.write("}");
    }
    @Override
    public Value call(Value argument)
    {
        if(argument instanceof SymbolValue s)
        {
            if(s.name.compareString("key")) return element;
            if(s.name.compareString("value")) return value;
        }
        if(element.valueEquals(argument)) return value;
        else return NoneValue.instance;
    }
    @Override
    public boolean valueEquals(Value v)
    {
        return v == this;
    }
    @Override
    public Object toObject(Class<?> type)
    {
        throw new UnsupportedOperationException("Unimplemented method 'toObject'");
    }
    @Override
    public int valueCode()
    {
        return hashCode();
    }
}