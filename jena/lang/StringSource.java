package jena.lang;

public class StringSource implements Source
{
    String source;

    public StringSource(String source)
    {
        this.source = source;
    }

    @Override
    public void read(Position position, Count count, CharacterBuffer buffer)
    {
        int l = source.length();
        int start = position.position(0, l);
        int end = start + count.count(l);

        for (int i = start; i < end; i++) buffer.write(source.charAt(i));
    }
}