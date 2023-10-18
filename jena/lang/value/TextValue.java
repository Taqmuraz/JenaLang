package jena.lang.value;

import jena.lang.text.StringText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public final class TextValue implements Value
{
    private Text text;
    private ValueFunction elements;

    public TextValue(Text text)
    {
        this.text = text;
        elements = () -> new TupleValue(text.buffer().<Value>map(CharacterValue::new));
    }
    public TextValue(String text)
    {
        this(new StringText(text));
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write(text);
    }

    @Override
    public Value call(Value argument)
    {
        return elements.call().call(argument);
    }
    @Override
    public boolean valueEquals(Value v)
    {
        return v instanceof TextValue t && t.text.compare(text);
    }
}