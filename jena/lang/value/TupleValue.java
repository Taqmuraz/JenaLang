package jena.lang.value;

import jena.lang.ArrayBuffer;
import jena.lang.GenericBuffer;
import jena.lang.StructPair;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public final class TupleValue implements Value
{
    private GenericBuffer<Value> items;
    private Value members;

    public TupleValue(Value... items)
    {
        this(new ArrayBuffer<>(items));
    }

    public TupleValue(GenericBuffer<Value> items)
    {
        this.items = items;
        members = new SymbolMatchValue(action ->
        {
            action.call("size", () -> new NumberValue(items.length()));
            action.call("map", () -> new MethodValue(new TextValue("function"), arg ->
                new TupleValue(items.map(item ->
                arg.call(item)))));
            action.call("each", () -> new MethodValue(new TextValue("action"), arg ->
            {
                items.flow().read(item -> arg.call(item));
                return this;
            }));
            action.call("pipe", () -> new MethodValue(new TupleValue(
                new TextValue("input"),
                new TextValue("function")
            ), arg ->
            {
                var args = arg.decompose();

                Value[] output = { args.at(0) };
                Value function = args.at(1);
                items.each(item -> output[0] = function.call(
                        new TupleValue(output[0], item)));
                return output[0];
            }));
            action.call("join", () -> new MethodValue(new TextValue("separator"),
                arg -> new TupleValue(items.join(arg))));
        },
        argument -> items.at(new ExpressionNumber(argument).integer()));
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
    public GenericBuffer<Value> decompose()
    {
        return items;
    }

    @Override
    public Value call(Value argument)
    {
        var arguments = argument.decompose();
        if(arguments.length() == 0) return items.at(items.length() - 1);
        return members.call(argument);
    }

    @Override
    public boolean valueEquals(Value v)
    {
        var d = v.decompose();
        return d.length() == items.length() && items.zip(d).map(StructPair::new).all(s -> s.a.valueEquals(s.b));
    }
}