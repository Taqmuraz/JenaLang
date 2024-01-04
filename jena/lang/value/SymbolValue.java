package jena.lang.value;

import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public class SymbolValue implements Value
{
    public final String name;

    public SymbolValue(Text name)
    {
        this.name = name.string();
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write(".");
        writer.write(name);
    }

    @Override
    public Value call(Value argument)
    {
        if(argument instanceof SymbolValue) return NoneValue.instance;
        return argument.call(this);
    }

    @Override
    public boolean valueEquals(Value v)
    {
        return v instanceof SymbolValue s && s.name.equals(name);
    }

    @Override
    public Object toObject(Class<?> type)
    {
        throw new UnsupportedOperationException("Unimplemented method 'toObject' for symbol %s".formatted(name));
    }

    @Override
    public int valueCode()
    {
        return name.hashCode();
    }
}