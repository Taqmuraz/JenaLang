package jena.lang.syntax;

import jena.lang.ArrayGenericBuffer;
import jena.lang.GenericPair;
import jena.lang.source.SingleCharacterSource;
import jena.lang.source.Source;
import jena.lang.value.Namespace;
import jena.lang.value.TextValue;
import jena.lang.value.TupleValue;
import jena.lang.value.Value;

public final class MemberExpressionSyntax implements Syntax
{
    private Source name;
    private Syntax expression;

    public MemberExpressionSyntax(Source name, Syntax expression)
    {
        this.name = name;
        this.expression = expression;
    }

    @Override
    public void source(SyntaxSerializer writer)
    {
        writer.source(name);
        writer.source(new SingleCharacterSource(':'));
        expression.source(writer);
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

    public GenericPair<Source, Syntax> nameExpression()
    {
        return action -> action.call(name, expression);
    }
}