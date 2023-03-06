package jena.syntax;

import jena.lang.source.SingleCharacterSource;
import jena.lang.source.StringSource;

public final class MathBinaryOperatorSyntax implements BinaryOperatorSyntax
{
    private char operator;

    public MathBinaryOperatorSyntax(char operator)
    {
        this.operator = operator;
    }

    @Override
    public void source(SyntaxSerializer writer)
    {
        writer.source(new SingleCharacterSource(operator));
    }

    @Override
    public Syntax classicExpression(Syntax left, Syntax right)
    {
        String name;
        switch(operator)
        {
            case '+':name = "add"; break;
            case '-':name = "sub"; break;
            case '*':name = "mul"; break;
            case '/':name = "div"; break;
            default:name = "unknownOperator";
        }
        return new InvocationExpressionSyntax(new MemberAccessExpressionSyntax(left, new StringSource(name)), right);
    }

    @Override
    public Syntax decomposed()
    {
        return this;
    }

    @Override
    public Value value(Namespace namespace)
    {
        return NoneValue.instance;
    }
}