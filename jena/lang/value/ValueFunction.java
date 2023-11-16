package jena.lang.value;

public interface ValueFunction
{
    Value call();

    static ValueFunction of(Value value)
    {
        if(value instanceof NoneValue) return none;
        return () -> value;
    }

    static final ValueFunction none = () -> NoneValue.instance;
}