package jena.lang.value;

import jena.lang.text.TextWriter;
import jena.lang.text.ValueText;

public interface Value
{
    void print(TextWriter writer);
    Value call(Value argument);
    boolean valueEquals(Value v);

    default String string()
    {
        return new ValueText(this).string();
    }

    default char toChar()
    {
        throw new RuntimeException(String.format("Value %s must have type of char", string()));
    }
    default int toInt()
    {
        throw new RuntimeException(String.format("Value %s must have type of int", string()));
    }
    default byte toByte()
    {
        throw new RuntimeException(String.format("Value %s must have type of int", string()));
    }
    default long toLong()
    {
        throw new RuntimeException(String.format("Value %s must have type of long", string()));
    }
    default float toFloat()
    {
        throw new RuntimeException(String.format("Value %s must have type of float", string()));
    }
    default double toDouble()
    {
        throw new RuntimeException(String.format("Value %s must have type of double", string()));
    }
}