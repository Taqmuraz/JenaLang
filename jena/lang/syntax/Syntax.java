package jena.lang.syntax;

import jena.lang.text.TextWriter;
import jena.lang.value.ValueProducer;

public interface Syntax extends ValueProducer
{
    void text(TextWriter writer);
    Syntax decomposed();
}