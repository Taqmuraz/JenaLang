package jena.lang.text;

public class ConcatText implements Text
{
    Text a;
    Text b;

    public ConcatText(Text a, Text b)
    {
        this.a = a;
        this.b = b;
    }
    @Override
    public char at(int index)
    {
        int al = a.length();
        if(index < al) return a.at(index);
        return b.at(index - al);
    }
    @Override
    public int length()
    {
        return a.length() + b.length();
    }
}