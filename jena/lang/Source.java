package jena.lang;

public interface Source
{
    void read(Position position, Count count, SourceReader buffer);
}