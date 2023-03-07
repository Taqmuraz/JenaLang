package jena.lang.syntax;

public interface BinaryOperatorSyntax extends Syntax
{
    Syntax classicExpression(Syntax left, Syntax right);
}