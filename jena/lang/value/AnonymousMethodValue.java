package jena.lang.value;

import jena.lang.GenericBuffer;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.StringText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public final class AnonymousMethodValue implements Value
{
    private GenericBuffer<Text> arguments;
    private ValueListFunction function;

    public AnonymousMethodValue(GenericBuffer<Text> arguments, ValueListFunction function)
    {
        this.arguments = arguments;
        this.function = function;
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write(new StringText("anonymous_method"));
        writer.write(new SingleCharacterText('('));
        Text separator = new SingleCharacterText(',');
        arguments.join(separator).each(writer::write);
        writer.write(new SingleCharacterText(')'));
    }

    @Override
    public Value member(Text name)
    {
        return NoneValue.instance;
    }

    @Override
    public Value call(ArgumentList arguments)
    {
        return arguments.number(this.arguments.length(), function, () -> NoneValue.instance);
    }
}