package jena.lang.value;

import jena.lang.GenericBuffer;
import jena.lang.GenericPair;
import jena.lang.SingleBuffer;
import jena.lang.StructPair;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.StringText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public final class TupleValue implements Value
{
    private GenericBuffer<Value> items;
    private Value members;

    public TupleValue(GenericBuffer<Value> items)
    {
        this.items = items;
        members = new NamespaceValue(
            new SingleBuffer<GenericPair<Text, Value>>(
                new StructPair<>(
                    new StringText("size"),
                    new IntegerValue(items.length()))).flow()
                    .append(
                        new StructPair<>(
                            new StringText("map"),
                            new MethodValue(new SingleBuffer<>(new StringText("function")), args ->
                                new TupleValue(items.map(item ->
                                args.at(0).call(new SingleArgumentList(item)))))))
                    .append(
                        new StructPair<>(
                            new StringText("each"),
                            new MethodValue(new SingleBuffer<>(new StringText("action")), args ->
                            {
                                items.flow().read(item -> args.at(0).call(new SingleArgumentList(item)));
                                return this;
                            })
                    ))
                    .append(
                        new StructPair<>(
                            new StringText("join"),
                            new MethodValue(new SingleBuffer<>(new StringText("separator")), args -> new TupleValue(items.join(args.at(0))))
                        )
                    ).collect());
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
    public Value member(Text name)
    {
        return members.member(name);
    }

    @Override
    public Value call(ArgumentList arguments)
    {
        return arguments.number(0, args -> items.at(items.length() - 1), () -> arguments.number(1, args ->
        {
            return items.at(new ExpressionIntegerNumber(args.at(0)).integer());
        }, () -> NoneValue.instance));
    }
}