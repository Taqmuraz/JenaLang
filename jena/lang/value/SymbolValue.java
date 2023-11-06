package jena.lang.value;

import jena.lang.text.ConcatText;
import jena.lang.text.StringText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public class SymbolValue implements Value
{
    public final Text name;

    public SymbolValue(Text name)
    {
        this.name = name;
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write(new ConcatText(new StringText("."), name));
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
        return v instanceof SymbolValue s && s.name.compare(name);
    }

    @Override
    public Object toObject(Class<?> type)
    {
        throw new UnsupportedOperationException("Unimplemented method 'toObject'");
    }

    @Override
    public int valueCode()
    {
        return name.hashCode();
    }
}