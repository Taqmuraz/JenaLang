package jena.lang.syntax;

import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.SingleNamespace;
import jena.lang.value.Namespace;
import jena.lang.value.Value;

public final class AssignmentExpressionSyntax implements Syntax
{
    private Syntax expression;
    private Text name;
    private Syntax next;
    
    public AssignmentExpressionSyntax(Syntax expression, Text name, Syntax next)
    {
        this.expression = expression;
        this.name = name;
        this.next = next;
    }

    @Override
    public void text(TextWriter writer)
    {
        writer.write("(");
        writer.write(name);
        writer.write("=");
        expression.text(writer);
        writer.write(")");
        next.text(writer);
    }

    @Override
    public Syntax decomposed()
    {
        return new AssignmentExpressionSyntax(expression.decomposed(), name, next.decomposed());
    }

    @Override
    public Value value(Namespace namespace)
    {
        return next.value(namespace.nested(new SingleNamespace(name, expression.value(namespace))));
    }
}