package jena.lang.value;

import jena.lang.text.Text;

public interface Single
{
    double single();

    default int integer()
    {
        return (int)single();
    }

    static Single of(Value value)
    {
        if(value instanceof Single) return (Single)value;
        else if(value instanceof JavaObjectValue j && j.obj instanceof Number n) return new NumberValue(n.doubleValue());
        else throw new RuntimeException("Not a number : %s".formatted(value.string()));
    }
    static NumberValue of(Text text)
    {
        return new NumberValue(Double.valueOf(text.string()));
    }
}