package jena.lang.value;

import java.lang.reflect.Proxy;
import java.util.function.Supplier;

import jena.lang.ArrayBuffer;
import jena.lang.text.StringText;
import jena.lang.text.TextWriter;

public final class FunctionValue implements Value
{
    public interface RecurCallFunction
    {
        Value call(Value arg, Value self);
    }

    private Supplier<String> argument;
    private ValueCallFunction function;

    public FunctionValue(String argumentName, ValueCallFunction function)
    {
        this.argument = () -> argumentName;
        this.function = function;
    }
    public FunctionValue(Supplier<String> argumentName, ValueCallFunction function)
    {
        this.argument = argumentName;
        this.function = function;
    }
    public FunctionValue(ValueCallFunction function)
    {
        this.argument = NoneValue.instance::string;
        this.function = function;
    }
    public FunctionValue(String argumentName, RecurCallFunction function)
    {
        this.argument = () -> argumentName;
        this.function = arg -> function.call(arg, this);
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write(argument.get());
        writer.write(new StringText("->..."));
    }

    @Override
    public Value call(Value arg)
    {
        return function.call(arg);
    }
    @Override
    public boolean valueEquals(Value v)
    {
        return v == this;
    }
    @Override
    public Object toObject(Class<?> type)
    {
        return Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] { type }, (p, method, args) ->
        {
            Value varg;
            if(args.length == 0)
            {
                varg = NoneValue.instance;
            }
            else
            {
                varg = new ArrayValue(new ArrayBuffer<Object>(args).map(o -> JavaObjectValue.fromObject(o)));
            }
            return function.call(new ArrayValue(varg)).toObject(method.getReturnType());
        });
    }
}