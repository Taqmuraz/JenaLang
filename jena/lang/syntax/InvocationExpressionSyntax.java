package jena.lang.syntax;

import jena.lang.GenericFlow;
import jena.lang.value.BufferArgumentList;
import jena.lang.value.Namespace;
import jena.lang.value.Value;

public final class InvocationExpressionSyntax implements Syntax
{
    private Syntax expression;
    private GenericFlow<Syntax> arguments;
    private Syntax argumentList;

    public InvocationExpressionSyntax(Syntax expression, GenericFlow<Syntax> arguments)
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
        return new InvocationExpressionSyntax(expression.decomposed(), arguments.map(a -> a.decomposed()));
    }

    @Override
    public Value value(Namespace namespace)
    {
        return expression.value(namespace).call(new BufferArgumentList(arguments.map(a -> a.value(namespace)).collect()));
    }
}