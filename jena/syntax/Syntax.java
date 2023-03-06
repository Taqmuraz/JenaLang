package jena.syntax;

public interface Syntax
{
    void source(SyntaxSerializer writer);
    Syntax decomposed();
    Value value(Namespace namespace);
}