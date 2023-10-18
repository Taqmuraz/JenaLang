package jena.lang.value;

import jena.lang.text.SingleCharacterText;
import jena.lang.text.TextWriter;

public final class CharacterValue implements Value
{
    private Character symbol;

    public CharacterValue(Character symbol)
    {
        this.symbol = symbol;
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write(new SingleCharacterText(symbol));
    }

    @Override
    public Value call(Value argument)
    {
        return NoneValue.instance;
    }

    @Override
    public boolean valueEquals(Value v)
    {
        return v instanceof CharacterValue c && c.symbol.equals(symbol);
    }
}