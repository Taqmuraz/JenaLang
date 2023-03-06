package jena.syntax;

import java.util.Arrays;

import jena.lang.value.ArrayArgumentList;
import jena.lang.value.Namespace;
import jena.lang.value.Value;

public final class InvocationExpressionSyntax implements Syntax
{
    private Syntax expression;
    private Syntax[] arguments;
    private Syntax argumentList;

    public InvocationExpressionSyntax(Syntax expression, Syntax... arguments)
    {
        this.expression = expression;
        this.arguments = arguments;
        this.argumentList = new ExpressionListSyntax(arguments);
    }

    @Override
    public void source(SyntaxSerializer writer)
    {
        expression.source(writer);
        argumentList.source(writer);
    }

    @Override
    public Syntax decomposed()
    {
        return new InvocationExpressionSyntax(expression.decomposed(), Arrays.stream(arguments).map(a -> a.decomposed()).toArray(Syntax[]::new));
    }

    @Override
    public Value value(Namespace namespace)
    {
        return expression.value(namespace).call(new ArrayArgumentList(Arrays.stream(arguments).map(a -> a.value(namespace)).toArray(Value[]::new)));
    }
}