package jena.syntax;

import jena.lang.source.SingleCharacterSource;

public final class ArgumentList implements Syntax
{
    private Syntax[] arguments;

    public ArgumentList(Syntax... arguments) {
        this.arguments = arguments;
    }

    @Override
    public void source(SyntaxSerializer writer)
    {
        writer.source(new SingleCharacterSource('('));
        new JoinExpressionSyntax(new SingleCharacterSource(' '), arguments).source(writer);
        writer.source(new SingleCharacterSource(')'));
    }
}