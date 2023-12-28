package jena.lang.syntax;

import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.FunctionValue;
import jena.lang.value.HashMapNamespace;
import jena.lang.value.Namespace;
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
        var argText = Text.of(argument).string();
        ValueFunction[] self = { null };
        return self[0] = ValueFunction.of(new FunctionValue(argText, arg ->
        {
            return expression.value(namespace.nested(new HashMapNamespace(action ->
            {
                action.call(argText, ValueFunction.of(arg));
                action.call("self", self[0]);
            }))).call();
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