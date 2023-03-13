package jena.lang.text;

public final class StringText implements Text
{
    private String string;

    public StringText(String string)
    {
        this.string = string;
    }

    @Override
    public char at(int index)
    {
        return string.charAt(index);
    }

    @Override
    public int length()
    {
        return string.length();
    }

    @Override
    public String toString()
    {
        return "deprecated";
    }
}