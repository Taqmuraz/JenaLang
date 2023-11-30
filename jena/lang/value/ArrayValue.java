package jena.lang.value;

import java.lang.reflect.Array;

import jena.lang.ArrayBuffer;
import jena.lang.GenericBuffer;
import jena.lang.StructPair;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public final class ArrayValue implements Value
{
    public final GenericBuffer<Value> items;
    private Value members;

    public ArrayValue(Value... items)
    {
        this(new ArrayBuffer<>(items));
    }

    public ArrayValue(GenericBuffer<Value> items)
    {
        this.items = items;
        members = new SymbolMapValue(action ->
        {
            action.call("size", () -> new NumberValue(items.length()));
            action.call("map", () -> new FunctionValue("function", arg ->
                new ArrayValue(items.map(item ->
                arg.call(item)))));
            action.call("each", () -> new FunctionValue("action", arg ->
            {
                items.flow().read(item -> arg.call(item));
                return this;
            }));
            action.call("filter", () -> new FunctionValue("condition", arg ->
            {
                return new ArrayValue(items.flow().filter(v -> new ExpressionNumber(arg.call(v)).integer() == 1).collect());
            }));
            action.call("pipe", () -> new FunctionValue("input", input -> new FunctionValue("function", function ->
            {
                Value[] output = { input };
                items.each(item -> output[0] = function.call(
                        new ArrayValue(output[0], item)));
                return output[0];
            })));
            action.call("join", () -> new FunctionValue("separator",
                arg -> new ArrayValue(items.join(arg))));
        },
        argument ->
        {
            var num = new ExpressionNumber(argument).integer();
            if(num < items.length() && num >= 0) return items.at(num);
            else return NoneValue.instance;
        });
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write(new SingleCharacterText('['));
        Text separator = new SingleCharacterText(',');
        
        items.flow().join(item -> item.print(writer), () -> writer.write(separator));

        writer.write(new SingleCharacterText(']'));
    }

    @Override
    public Value call(Value argument)
    {
        return members.call(argument);
    }

    @Override
    public boolean valueEquals(Value v)
    {
        return v instanceof ArrayValue array && array.items.length() == items.length() && items.zip(array.items).map(StructPair::new).all(s -> s.a.valueEquals(s.b));
    }

    @Override
    public Object toObject(Class<?> type)
    {
        if(type.isArray())
        {
            int length = items.length();
            var element = type.getComponentType();
            Object[] array = (Object[])Array.newInstance(element, length);
            for(int i = 0; i < length; i++)
            {
                array[i] = items.at(i).toObject(element);
            }
            return array;
        }
        else throw new RuntimeException(String.format("%s is not an array type", type.getName()));
    }

    @Override
    public int valueCode()
    {
        return hashCode();
    }
}