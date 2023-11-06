package jena.lang.syntax;

public interface SyntaxUnit
{
    Syntax complete();

    static SyntaxUnit of(Syntax syntax)
    {
        return () -> syntax;
    }
}