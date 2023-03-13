package jena.lang.syntax;

import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.Namespace;
import jena.lang.value.TupleValue;
import jena.lang.value.Value;
import jena.lang.GenericFlow;
import jena.lang.JoinAction;

public final class JoinExpressionSyntax implements Syntax
{
    private Text separator;
    private GenericFlow<Syntax> expressions;

    public JoinExpressionSyntax(Text separator, GenericFlow<Syntax> expressions)
    {
        this.separator = separator;
        this.expressions = expressions;
    }

    @Override
    public void text(TextWriter writer)
    {
        new JoinAction<Syntax>(expressions, e -> e.text(writer), () -> writer.write(separator)).join();
    }

    @Override
    public Syntax decomposed()
    {
        return new JoinExpressionSyntax(separator, expressions.map(e -> e.decomposed()));
    }

    @Override
    public Value value(Namespace namespace)
    {
        return new TupleValue(expressions.map(e -> e.value(namespace)).collect());
    }
}