package jena.lang.value;

import jena.lang.text.SingleCharacterText;
import jena.lang.text.TextWriter;

public final class CharacterValue implements Value
{
    public final Character symbol;

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

    @Override
    public Object toObject(Class<?> type)
    {
        if(type == Character.TYPE || type == Character.class) return symbol;
        else throw new RuntimeException(String.format("%s is not a Character type", type.getName()));
    }

    @Override
    public int valueCode()
    {
        return symbol.hashCode();
    }
}