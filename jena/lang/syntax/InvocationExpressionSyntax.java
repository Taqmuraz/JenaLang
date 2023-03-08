package jena.lang.syntax;

import jena.lang.GenericBuffer;
import jena.lang.source.SingleCharacterSource;
import jena.lang.value.BufferArgumentList;
import jena.lang.value.Namespace;
import jena.lang.value.Value;

public final class InvocationExpressionSyntax implements Syntax
{
    private Syntax expression;
    private GenericBuffer<Syntax> arguments;
    private Syntax argumentList;

    public InvocationExpressionSyntax(Syntax expression, GenericBuffer<Syntax> arguments)
    {
        this.expression = expression;
        this.arguments = arguments;
        this.argumentList = new ExpressionListSyntax(arguments, new SingleCharacterSource('('), new SingleCharacterSource(')'));
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
        return new InvocationExpressionSyntax(expression.decomposed(), arguments.flow().map(a -> a.decomposed()).collect());
    }

    @Override
    public Value value(Namespace namespace)
    {
        return expression.value(namespace).call(new BufferArgumentList(arguments.flow().map(a -> a.value(namespace)).collect()));
    }
}