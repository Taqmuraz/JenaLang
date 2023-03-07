package jena.lang.syntax;

import jena.lang.value.Namespace;
import jena.lang.value.Value;

public interface Syntax
{
    void source(SyntaxSerializer writer);
    Syntax decomposed();
    Value value(Namespace namespace);
}