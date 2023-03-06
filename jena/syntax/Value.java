package jena.syntax;

import jena.lang.source.Source;
import jena.lang.source.SourceAction;

public interface Value
{
    void print(SourceAction action);
    Value member(Source name);
    Value call(ArgumentList arguments);
}