package jena.lang.syntax;

import jena.lang.ArrayGenericBuffer;
import jena.lang.GenericPair;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.Namespace;
import jena.lang.value.TextValue;
import jena.lang.value.TupleValue;
import jena.lang.value.Value;
import jena.lang.value.ValueProducer;

public final class MemberExpressionSyntax implements Syntax
{
    private Text name;
    private Syntax expression;

    public MemberExpressionSyntax(Text name, Syntax expression)
    {
        this.name = name;
        this.expression = expression;
    }

    @Override
    public void text(TextWriter writer)
    {
        writer.write(name);
        writer.write(new SingleCharacterText(':'));
        expression.text(writer);
    }

    @Override
    public Syntax decomposed()
    {
        return new MemberExpressionSyntax(name, expression.decomposed());
    }

    @Override
    public Value value(Namespace namespace)
    {
        return new TupleValue(new ArrayGenericBuffer<Value>(new Value[] { new TextValue(name), expression.value(namespace) }));
    }

    public GenericPair<Text, ValueProducer> nameExpression()
    {
        return action -> action.call(name, expression);
    }
}