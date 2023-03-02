package jena.lang.source;

public class StringSourceReader implements SourceSymbolAction
{
    StringBuilder stringBuilder = new StringBuilder();

    @Override
    public void call(char symbol, int number)
    {
        stringBuilder.append(symbol);
    }

    @Override
    public String toString()
    {
        return stringBuilder.toString();
    }
}