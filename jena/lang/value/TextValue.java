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
        elements = () -> new ArrayValue(text.buffer().<Value>map(CharacterValue::new));
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
        if(argument instanceof SymbolValue symbol)
        {
            if(symbol.name.compare(Text.of("add")))
            {
                return new FunctionValue("addition", arg -> new TextValue(text.concat(Text.of(arg))));
            }
            else if(symbol.name.compare(Text.of("buff")))
            {
                return new TextValue(Text.of(text.string()));
            }
            else if(symbol.name.compare(Text.of("equals")))
            {
                return new FunctionValue("equalTo", arg -> new NumberValue(arg instanceof TextValue t && t.text.compare(text)));
            }
            else if(symbol.name.compare(Text.of("notEquals")))
            {
                return new TextValue(Text.of(text.string()));
            }
        }
        return elements.call().call(argument);
    }
    @Override
    public boolean valueEquals(Value v)
    {
        return v instanceof TextValue t && t.text.compare(text);
    }
    @Override
    public Object toObject(Class<?> type)
    {
        if(type.isAssignableFrom(String.class)) return text.string();
        else throw new RuntimeException(String.format("%s is not assignable from the String type", type.getName()));
    }
    @Override
    public int valueCode()
    {
        return text.string().hashCode();
    }
}