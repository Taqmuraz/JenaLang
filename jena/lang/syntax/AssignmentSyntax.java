package jena.lang.syntax;

import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.SingleNamespace;
import jena.lang.value.Namespace;
import jena.lang.value.ValueFunction;

public final class AssignmentSyntax implements Syntax
{
    private Syntax expression;
    private Text name;
    private Syntax next;
    
    public AssignmentSyntax(Text name, Syntax expression, Syntax next)
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
        writer.write("->");
        next.text(writer);
        writer.write(")");
    }

    @Override
    public ValueFunction value(Namespace namespace)
    {
        var expr = expression.value(namespace);
        return next.value(namespace.nested(new SingleNamespace(name, expr)));
    }
}