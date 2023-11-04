package jena.lang.syntax;

import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.FunctionValue;
import jena.lang.value.Namespace;
import jena.lang.value.SingleElementMapValue;
import jena.lang.value.SymbolValue;
import jena.lang.value.Value;

public final class BindingConstructorExpressionSyntax implements Syntax
{
    Text name;

    public BindingConstructorExpressionSyntax(Text name)
    {
        this.name = name;
    }

    @Override
    public Value value(Namespace namespace)
    {
        return new FunctionValue("value", arg -> new SingleElementMapValue(new SymbolValue(name), arg));
    }

    @Override
    public void text(TextWriter writer)
    {
        writer.write(name);
        writer.write(":");
    }

    @Override
    public Syntax decomposed()
    {
        return this;
    }
}