package jena.lang.value;

import jena.lang.ArrayBuffer;
import jena.lang.GenericBuffer;
import jena.lang.text.TextWriter;

public final class SingleElementMapValue implements Value
{
    Value element;
    Value value;

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
        if(element.valueEquals(argument)) return value;
        else return NoneValue.instance;
    }
    @Override
    public boolean valueEquals(Value v)
    {
        return v == this;
    }
    @Override
    public GenericBuffer<Value> decompose()
    {
        return new ArrayBuffer<>(element, value);
    }
}