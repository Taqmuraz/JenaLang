package jena.lang.value;

public final class EmptyArgumentList implements ArgumentList
{
    public static final ArgumentList instance = new EmptyArgumentList();

    @Override
    public Value number(int number, ValueListFunction action, ValueFunction fail)
    {
        if(number == 0) return action.call(i -> NoneValue.instance);
        return fail.call();
    }
}