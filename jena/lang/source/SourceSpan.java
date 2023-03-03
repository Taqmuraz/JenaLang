package jena.lang.source;

public interface SourceSpan
{
    Source at(int index);
    SourceSpan skip(int count);
}