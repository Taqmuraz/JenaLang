package jena.lang.syntax;

import jena.lang.text.SingleCharacterText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.Namespace;
import jena.lang.value.Value;

public final class MemberAccessExpressionSyntax implements Syntax
{
    private Syntax expression;
    private Text memberName;

    public MemberAccessExpressionSyntax(Syntax expression, Text memberName)
    {
        this.expression = expression;
        this.memberName = memberName;
    }

    @Override
    public void text(TextWriter writer)
    {
        expression.text(writer);
        writer.write(new SingleCharacterText('.'));
        writer.write(memberName);
    }

    @Override
    public Syntax decomposed()
    {
        return new MemberAccessExpressionSyntax(expression.decomposed(), memberName);
    }

    @Override
    public Value value(Namespace namespace)
    {
        return expression.value(namespace).member(memberName);
    }
}