package jena.syntax;

public class BinaryExpressionSyntax implements Syntax
{
    private Syntax left;
    private Syntax right;
    private BinaryOperatorSyntax operator;
    
    public BinaryExpressionSyntax(Syntax left, BinaryOperatorSyntax operator, Syntax right)
    {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public void source(SyntaxSerializer writer)
    {
        operator.classicExpression(left, right).source(writer);
    }
}