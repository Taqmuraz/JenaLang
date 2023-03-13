package jena.lang.source;

public interface SourceLocation
{
    void location(int position, SourceLocationAction action);
}