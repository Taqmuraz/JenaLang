package jena.lang;

public interface SourcePhase
{
    SourceFlow pass(SourceFlow sourceFlow);
}