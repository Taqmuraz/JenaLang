package jena.lang.text;

import jena.lang.value.Value;

public final class ValueText implements Text
{
    private Text text;

    public ValueText(Value value)
    {
        StringBuilder sb = new StringBuilder();
        value.print(text -> sb.append(text.string()));
        text = new StringText(sb.toString());
    }

    @Override
    public char at(int index)
    {
        return text.at(index);
    }

    @Override
    public int length()
    {
        return text.length();
    }
}