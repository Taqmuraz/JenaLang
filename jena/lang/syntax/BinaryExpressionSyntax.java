package jena.lang.syntax;

import jena.lang.text.TextWriter;
import jena.lang.value.Namespace;
import jena.lang.value.Value;

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
    public void text(TextWriter writer)
    {
        left.text(writer);
        operator.text(writer);
        right.text(writer);
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