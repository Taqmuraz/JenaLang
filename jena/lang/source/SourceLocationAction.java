package jena.lang.source;

public interface SourceLocationAction
{
    void call(int line, int symbol);
}