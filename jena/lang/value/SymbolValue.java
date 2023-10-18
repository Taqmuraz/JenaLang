package jena.lang.value;

import jena.lang.text.ConcatText;
import jena.lang.text.StringText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public class SymbolValue implements Value
{
    Text name;

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
        return NoneValue.instance;
    }

    @Override
    public boolean valueEquals(Value v)
    {
        return v instanceof SymbolValue s && s.name.compare(name);
    }
}