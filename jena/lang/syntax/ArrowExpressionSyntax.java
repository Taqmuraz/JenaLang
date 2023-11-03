package jena.lang.syntax;

import jena.lang.text.SyntaxText;
import jena.lang.text.TextWriter;
import jena.lang.value.FunctionValue;
import jena.lang.value.Namespace;
import jena.lang.value.SingleNamespace;
import jena.lang.value.Value;

public final class ArrowExpressionSyntax implements Syntax
{
    public ArrowExpressionSyntax(Syntax argument, Syntax expression)
    {
        this.argument = argument;
        this.expression = expression;
    }

    Syntax argument;
    Syntax expression;

    @Override
    public Value value(Namespace namespace)
    {
        var argText = new SyntaxText(argument);
        return new FunctionValue(argText.string(), arg -> expression.value(
            namespace.nested(
                new SingleNamespace(argText, arg))));
    }

    @Override
    public void text(TextWriter writer)
    {
        argument.text(writer);
        writer.write("->");
        expression.text(writer);
    }

    @Override
    public Syntax decomposed()
    {
        return new ArrowExpressionSyntax(argument.decomposed(), expression.decomposed());
    }
}