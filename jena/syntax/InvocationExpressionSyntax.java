package jena.syntax;

public final class InvocationExpressionSyntax implements Syntax
{
    private Syntax expression;
    private Syntax argumentList;

    public InvocationExpressionSyntax(Syntax expression, Syntax... arguments)
    {
        this.expression = expression;
        this.argumentList = new ArgumentList(arguments);
    }

    @Override
    public void source(SyntaxSerializer writer)
    {
        expression.source(writer);
        argumentList.source(writer);
    }
}