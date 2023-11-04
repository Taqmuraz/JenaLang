package jena.lang.value;

import jena.lang.text.TextWriter;
import jena.lang.text.ValueText;

public interface Value
{
    void print(TextWriter writer);
    Value call(Value argument);
    boolean valueEquals(Value v);
    Object toObject(Class<?> type);

    default String string()
    {
        return new ValueText(this).string();
    }
}