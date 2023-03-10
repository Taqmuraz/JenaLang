package jena.lang.syntax;

import jena.lang.SingleGenericBuffer;
import jena.lang.source.Source;
import jena.lang.source.StringSource;
import jena.lang.value.Namespace;
import jena.lang.value.NoneValue;
import jena.lang.value.Value;

public final class MathBinaryOperatorSyntax implements BinaryOperatorSyntax
{
    private Source operator;

    public MathBinaryOperatorSyntax(Source operator)
    {
        this.operator = operator;
    }

    @Override
    public void source(SyntaxSerializer writer)
    {
        writer.source(operator);
    }

    @Override
    public Syntax classicExpression(Syntax left, Syntax right)
    {
        String value = operator.text().toString();
        String name;
        switch(value)
        {
            case "+":name = "add"; break;
            case "-":name = "sub"; break;
            case "*":name = "mul"; break;
            case "/":name = "div"; break;
            case "<":name = "less"; break;
            case ">":name = "greater"; break;
            case "==":name = "equals"; break;
            case "!=":name = "notEquals"; break;
            default:name = "unknownOperator";
        }
        return new InvocationExpressionSyntax(new MemberAccessExpressionSyntax(left, new StringSource(name)), new SingleGenericBuffer<Syntax>(right));
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