package jena.lang.value;

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
        else if(value instanceof NoneValue) return new NumberValue(0);
        else return new NumberValue(1);
    }
}