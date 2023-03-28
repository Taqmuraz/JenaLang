package jena.lang.syntax;

import jena.lang.GenericBuffer;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.StringText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.PairNamespace;
import jena.lang.value.Namespace;
import jena.lang.value.Value;

public final class UsingExpressionSyntax implements Syntax
{
    private GenericBuffer<Syntax> expressions;
    private GenericBuffer<Text> names;
    private Syntax expression;
    
    public UsingExpressionSyntax(GenericBuffer<Syntax> expressions, GenericBuffer<Text> names, Syntax expression)
    {
        this.expressions = expressions;
        this.names = names;
        this.expression = expression;
    }

    @Override
    public void text(TextWriter writer)
    {
        writer.write(new StringText("using"));
        writer.write(new SingleCharacterText('('));
        expressions.flow().join(e -> e.text(writer), () -> writer.write(new SingleCharacterText(',')));
        writer.write(new SingleCharacterText(')'));
        writer.write(new StringText("as"));
        writer.write(new SingleCharacterText('('));
        names.flow().join(n -> writer.write(n), () -> writer.write(new SingleCharacterText(',')));
        writer.write(new SingleCharacterText(')'));
        expression.text(writer);
    }

    @Override
    public Syntax decomposed()
    {
        return new UsingExpressionSyntax(expressions.flow().map(e -> e.decomposed()).collect(), names, expression.decomposed());
    }

    @Override
    public Value value(Namespace namespace)
    {
        return expression.value(namespace.nested(new PairNamespace(names.flow().zip(expressions.flow().map(e -> e.value(namespace))).collect())));
    }
}