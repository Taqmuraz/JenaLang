package jena.lang.value;

import jena.lang.GenericBuffer;
import jena.lang.SingleBuffer;
import jena.lang.text.TextWriter;

public interface Value
{
    void print(TextWriter writer);
    Value call(Value argument);
    boolean valueEquals(Value v);

    default GenericBuffer<Value> decompose()
    {
        return new SingleBuffer<>(this);
    }
}