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
        left.source(writer);
        operator.source(writer);
        right.source(writer);
    }

    @Override
    public Syntax decomposed()
    {
        return operator.classicExpression(left.decomposed(), right.decomposed());
    }

    @Override
    public Value value(Namespace namespace)
    {
        return decomposed().value(namespace);
    }
}