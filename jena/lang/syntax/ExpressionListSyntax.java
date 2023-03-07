package jena.lang.syntax;

import java.util.Arrays;

import jena.lang.source.SingleCharacterSource;
import jena.lang.value.Namespace;
import jena.lang.value.TupleValue;
import jena.lang.value.Value;

public final class ExpressionListSyntax implements Syntax
{
    private Syntax[] arguments;

    public ExpressionListSyntax(Syntax... arguments) {
        this.arguments = arguments;
    }

    @Override
    public void source(SyntaxSerializer writer)
    {
        writer.source(new SingleCharacterSource('('));
        new JoinExpressionSyntax(new SingleCharacterSource(' '), arguments).source(writer);
        writer.source(new SingleCharacterSource(')'));
    }

    @Override
    public Syntax decomposed()
    {
        return new ExpressionListSyntax(Arrays.stream(arguments).map(a -> a.decomposed()).toArray(Syntax[]::new));
    }

    @Override
    public Value value(Namespace namespace)
    {
        return new TupleValue(Arrays.stream(arguments).map(a -> a.value(namespace)).toArray(Value[]::new));
    }
}