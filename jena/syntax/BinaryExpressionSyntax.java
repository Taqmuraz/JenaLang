package jena.syntax;

import jena.lang.source.StringSource;

public class BinaryExpressionSyntax implements Syntax
{
    private Syntax left;
    private Syntax right;
    private Syntax operator;
    
    public BinaryExpressionSyntax(Syntax left, Syntax operator, Syntax right)
    {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public void source(SyntaxSerializer writer)
    {
        writer.source(new StringSource("binary("));
        left.source(writer);
        writer.source(new StringSource(","));
        operator.source(writer);
        writer.source(new StringSource(","));
        right.source(writer);
        writer.source(new StringSource(")"));
    }
}