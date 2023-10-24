package jena.lang.value;

import jena.lang.text.TextWriter;

public interface Value
{
    void print(TextWriter writer);
    Value call(Value argument);
    boolean valueEquals(Value v);
}