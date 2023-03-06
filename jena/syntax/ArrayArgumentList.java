package jena.syntax;

public final class ArrayArgumentList implements ArgumentList
{
    private Value[] arguments;

    public ArrayArgumentList(Value... arguments)
    {
        this.arguments = arguments;
    }

    @Override
    public Value number(int number, ValueListFunction action, ValueFunction fail)
    {
        if(arguments.length == number) return action.call(i -> arguments[i]);
        return fail.call();
    }
}