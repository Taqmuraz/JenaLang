package jena.lang.syntax;

import jena.lang.GenericBuffer;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.TextWriter;
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
        this.argumentList = new ExpressionListSyntax(arguments, new SingleCharacterText('('), new SingleCharacterText(')'));
    }

    @Override
    public void text(TextWriter writer)
    {
        expression.text(writer);
        argumentList.text(writer);
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