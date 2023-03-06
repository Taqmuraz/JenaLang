package jena.syntax;

import java.util.Arrays;

import jena.lang.source.SingleCharacterSource;

public final class ArgumentListSyntax implements Syntax
{
    private Syntax[] arguments;

    public ArgumentListSyntax(Syntax... arguments) {
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
        return new ArgumentListSyntax(Arrays.stream(arguments).map(a -> a.decomposed()).toArray(Syntax[]::new));
    }

    @Override
    public Value value(Namespace namespace)
    {
        return new TupleValue(Arrays.stream(arguments).map(a -> a.value(namespace)).toArray(Value[]::new));
    }
}