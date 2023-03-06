package jena.syntax;

import java.util.Arrays;

import jena.lang.source.Source;
import jena.lang.value.Namespace;
import jena.lang.value.TupleValue;
import jena.lang.value.Value;

public final class JoinExpressionSyntax implements Syntax
{
    private Source separator;
    private Syntax[] expressions;

    public JoinExpressionSyntax(Source separator, Syntax... expressions)
    {
        this.separator = separator;
        this.expressions = expressions;
    }

    @Override
    public void source(SyntaxSerializer writer)
    {
        expressions[0].source(writer);
        for(int i = 1; i < expressions.length; i++)
        {
            writer.source(separator);
            expressions[i].source(writer);
        }
    }

    @Override
    public Syntax decomposed()
    {
        return new JoinExpressionSyntax(separator, Arrays.stream(expressions).map(e -> e.decomposed()).toArray(Syntax[]::new));
    }

    @Override
    public Value value(Namespace namespace)
    {
        return new TupleValue(Arrays.stream(expressions).map(e -> e.value(namespace)).toArray(Value[]::new));
    }
}