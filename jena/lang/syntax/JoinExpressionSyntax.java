package jena.lang.syntax;

import jena.lang.source.Source;
import jena.lang.value.Namespace;
import jena.lang.value.TupleValue;
import jena.lang.value.Value;
import jena.lang.GenericFlow;
import jena.lang.JoinAction;

public final class JoinExpressionSyntax implements Syntax
{
    private Source separator;
    private GenericFlow<Syntax> expressions;

    public JoinExpressionSyntax(Source separator, GenericFlow<Syntax> expressions)
    {
        this.separator = separator;
        this.expressions = expressions;
    }

    @Override
    public void source(SyntaxSerializer writer)
    {
        new JoinAction<Syntax>(expressions, e -> e.source(writer), () -> writer.source(separator)).join();
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