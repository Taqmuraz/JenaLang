package jena.lang.value;

import jena.lang.GenericBuffer;
import jena.lang.SingleGenericBuffer;

public final class SingleArgumentList implements ArgumentList
{
    private GenericBuffer<Value> argument;

    public SingleArgumentList(Value argument)
    {
        this.argument = new SingleGenericBuffer<Value>(argument);
    }

    @Override
    public Value number(int number, ValueListFunction action, ValueFunction fail)
    {
        if(number == 1) return action.call(argument);
        return fail.call();
    }
}