package jena.lang;

public interface Source
{
    void read(SourceReader sourceReader);

    static final Source empty = (s) -> {};
}