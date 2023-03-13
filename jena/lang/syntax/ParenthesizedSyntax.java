package jena.lang.syntax;

import jena.lang.text.SingleCharacterText;
import jena.lang.text.TextWriter;
import jena.lang.value.Namespace;
import jena.lang.value.Value;

public final class ParenthesizedSyntax implements Syntax
{
    private Syntax expression;

    public ParenthesizedSyntax(Syntax expression)
    {
        this.expression = expression;
    }

    @Override
    public void text(TextWriter writer)
    {
        writer.write(new SingleCharacterText('('));
        expression.text(writer);
        writer.write(new SingleCharacterText(')'));
    }

    @Override
    public Syntax decomposed()
    {
        return new ParenthesizedSyntax(expression.decomposed());
    }

    @Override
    public Value value(Namespace namespace)
    {
        return expression.value(namespace);
    }
}