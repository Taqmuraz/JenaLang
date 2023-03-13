package jena.lang.value;

import jena.lang.EmptyBuffer;

public final class EmptyArgumentList implements ArgumentList
{
    public static final ArgumentList instance = new EmptyArgumentList();

    @Override
    public Value number(int number, ValueListFunction action, ValueFunction fail)
    {
        if(number == 0) return action.call(new EmptyBuffer<Value>());
        return fail.call();
    }
}