package jena.lang.value;

import jena.lang.text.SingleCharacterText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public final class SymbolValue implements Value
{
    private Character symbol;

    public SymbolValue(Character symbol)
    {
        this.symbol = symbol;
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write(new SingleCharacterText(symbol));
    }

    @Override
    public Value member(Text name)
    {
        return NoneValue.instance;
    }

    @Override
    public Value call(ArgumentList arguments)
    {
        return NoneValue.instance;
    }
}