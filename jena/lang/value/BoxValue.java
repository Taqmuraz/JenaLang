package jena.lang.value;

import jena.lang.text.TextWriter;

public final class BoxValue implements Value
{
    Value value = NoneValue.instance;
    static MembersMap<BoxValue> members;

    public BoxValue(Value value)
    {
        this.value = value;
    }

    static
    {
        members = new MembersMap<>(action ->
        {
            action.call("get", b -> b.value);
            action.call("take", b -> b.value);
            action.call("set", b -> new FunctionValue("valueForBox", arg -> b.value = arg));
            action.call("put", b -> new FunctionValue("valueForBox", arg -> b.value = arg));
            action.call("apply", b -> new FunctionValue("functionToApply", arg -> b.value = arg.call(b.value)));
        });
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write("box(");
        value.print(writer);
        writer.write(")");
    }

    @Override
    public Value call(Value argument)
    {
        if(argument instanceof SymbolValue s)
        {
            return members.member(s.name, b -> value.call(argument)).call(this);
        }
        return value.call(argument);
    }

    @Override
    public boolean valueEquals(Value v)
    {
        return v == this;
    }

    @Override
    public int valueCode()
    {
        return value.valueCode();
    }

    @Override
    public Object toObject(Class<?> type)
    {
        return value.toObject(type);
    }
}