package jena.lang.syntax;

import jena.lang.value.ValueProducer;

public interface Syntax extends ValueProducer
{
    void source(SyntaxSerializer writer);
    Syntax decomposed();
}