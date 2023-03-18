package jena.lang.syntax;

import jena.lang.SingleBuffer;
import jena.lang.text.StringText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.Namespace;
import jena.lang.value.NoneValue;
import jena.lang.value.Value;

public final class MathBinaryOperatorSyntax implements BinaryOperatorSyntax
{
    private Text operator;

    public MathBinaryOperatorSyntax(Text operator)
    {
        this.operator = operator;
    }

    @Override
    public void text(TextWriter writer)
    {
        writer.write(operator);
    }

    @Override
    public Syntax classicExpression(Syntax left, Syntax right)
    {
        String value = operator.string();
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
            case "&":name = "and"; break;
            case "|":name = "or"; break;
            default:name = "unknownOperator";
        }
        return new InvocationExpressionSyntax(new MemberAccessExpressionSyntax(left, new StringText(name)), new SingleBuffer<Syntax>(right));
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