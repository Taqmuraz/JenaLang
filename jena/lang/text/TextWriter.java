package jena.lang.text;

public interface TextWriter
{
    void write(Text text);
    default void write(String text)
    {
        write(new StringText(text));
    }
}