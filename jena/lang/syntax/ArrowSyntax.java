package jena.lang.syntax;

import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.FunctionValue;
import jena.lang.value.Namespace;
import jena.lang.value.SingleNamespace;
import jena.lang.value.ValueFunction;

public final class ArrowSyntax implements Syntax
{
    public ArrowSyntax(Syntax argument, Syntax expression)
    {
        this.argument = argument;
        this.expression = expression;
    }

    Syntax argument;
    Syntax expression;

    @Override
    public ValueFunction value(Namespace namespace)
    {
        var argText = Text.of(argument);
        return ValueFunction.of(new FunctionValue(argText.string(), arg ->
        {
            return expression.value(namespace.nested(new SingleNamespace(argText, ValueFunction.of(arg)))).call();
        }));
    }

    @Override
    public void text(TextWriter writer)
    {
        argument.text(writer);
        writer.write("->");
        expression.text(writer);
    }
}