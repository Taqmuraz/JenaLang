package jena.lang.syntax;

import jena.lang.GenericPair;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.Namespace;
import jena.lang.value.SingleElementMapValue;
import jena.lang.value.SymbolValue;
import jena.lang.value.Value;
import jena.lang.value.ValueProducer;

public final class BindingExpressionSyntax implements Syntax
{
    private Text name;
    private Syntax expression;

    public BindingExpressionSyntax(Text name, Syntax expression)
    {
        this.name = name;
        this.expression = expression;
    }

    @Override
    public void text(TextWriter writer)
    {
        writer.write(new SingleCharacterText('{'));
        writer.write(name);
        writer.write(new SingleCharacterText(':'));
        expression.text(writer);
        writer.write(new SingleCharacterText('}'));
    }

    @Override
    public Syntax decomposed()
    {
        return new BindingExpressionSyntax(name, expression.decomposed());
    }

    @Override
    public Value value(Namespace namespace)
    {
        return new SingleElementMapValue(new SymbolValue(name), expression.value(namespace));
    }

    public GenericPair<Text, ValueProducer> nameValue()
    {
        return action -> action.call(name, expression);
    }
    public GenericPair<Text, Syntax> nameSyntax()
    {
        return action -> action.call(name, expression);
    }
}