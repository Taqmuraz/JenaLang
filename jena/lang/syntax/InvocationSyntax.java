package jena.lang.syntax;

import jena.lang.text.TextWriter;
import jena.lang.value.Namespace;
import jena.lang.value.Value;

public final class InvocationSyntax implements Syntax
{
    private Syntax expression;
    private Syntax argument;

    public InvocationSyntax(Syntax expression, Syntax argument)
    {
        this.expression = expression;
        this.argument = argument;
    }

    @Override
    public void text(TextWriter writer)
    {
        writer.write("(");
        expression.text(writer);
        writer.write(" ");
        argument.text(writer);
        writer.write(")");
    }

    @Override
    public Value value(Namespace namespace)
    {
        return expression.value(namespace).call(argument.value(namespace));
    }
}