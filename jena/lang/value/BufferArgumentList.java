package jena.lang.value;

import jena.lang.GenericBuffer;

public final class BufferArgumentList implements ArgumentList
{
    private GenericBuffer<Value> arguments;

    public BufferArgumentList(GenericBuffer<Value> arguments)
    {
        this.arguments = arguments;
    }

    @Override
    public Value number(int number, ValueListFunction action, ValueFunction fail)
    {
        if(arguments.length() == number) return action.call(arguments);
        return fail.call();
    }
}