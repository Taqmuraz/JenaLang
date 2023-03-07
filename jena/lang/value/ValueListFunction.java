package jena.lang.value;

import jena.lang.GenericBuffer;

public interface ValueListFunction
{
    Value call(GenericBuffer<Value> arguments);
}