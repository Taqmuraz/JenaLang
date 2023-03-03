package jena.syntax;

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
        left.source(writer);
        operator.source(writer);
        right.source(writer);
    }
}