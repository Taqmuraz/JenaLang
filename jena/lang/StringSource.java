package jena.lang;

public class StringSource implements Source
{
    String source;

    public StringSource(String source)
    {
        this.source = source;
    }

    @Override
    public void read(SourceReader sourceReader)
    {
        for (int i = 0; i < source.length(); i++) sourceReader.symbol(source.charAt(i));
    }
}