package jena.lang;

public class StringSource implements Source
{
    String source;

    public StringSource(String source)
    {
        this.source = source;
    }

    @Override
    public void read(Position position, Count count, SourceReader buffer)
    {
        int l = source.length();
        int start = position.position(0, l);
        int c = count.count(l - start);

        for (int i = 0; i < c; i++) buffer.write(source.charAt(i + start), i);
    }
}