package jena.lang.value;

import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public final class TextValue implements Value
{
    private Text text;
    private Value elements;

    public TextValue(Text text)
    {
        this.text = text;
        elements = new TupleValue(text.buffer().flow().<Value>map(SymbolValue::new).collect());
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write(text);
    }

    @Override
    public Value member(Text name)
    {
        return elements.member(name);
    }

    @Override
    public Value call(ArgumentList arguments)
    {
        return elements.call(arguments);
    }
}