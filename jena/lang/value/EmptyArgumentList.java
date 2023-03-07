package jena.lang.value;

import jena.lang.EmptyGenericBuffer;

public final class EmptyArgumentList implements ArgumentList
{
    public static final ArgumentList instance = new EmptyArgumentList();

    @Override
    public Value number(int number, ValueListFunction action, ValueFunction fail)
    {
        if(number == 0) return action.call(new EmptyGenericBuffer<Value>(NoneValue.instance));
        return fail.call();
    }
}