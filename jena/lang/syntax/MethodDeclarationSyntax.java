package jena.lang.syntax;

import jena.lang.GenericBuffer;
import jena.lang.source.SingleCharacterSource;
import jena.lang.source.Source;
import jena.lang.source.StringSource;
import jena.lang.value.PairNamespace;
import jena.lang.value.AnonymousMethodValue;
import jena.lang.value.Namespace;
import jena.lang.value.Value;

public final class MethodDeclarationSyntax implements Syntax
{
    private GenericBuffer<Syntax> arguments;
    private Syntax expression;
    
    public MethodDeclarationSyntax(GenericBuffer<Syntax> arguments, Syntax expression)
    {
        this.arguments = arguments;
        this.expression = expression;
    }

    @Override
    public void source(SyntaxSerializer writer)
    {
        writer.source(new StringSource("method"));
        writer.source(new SingleCharacterSource('('));
        arguments.flow().join(a -> a.source(writer), () -> writer.source(new SingleCharacterSource(' ')));
        writer.source(new SingleCharacterSource(')'));
        expression.source(writer);
    }

    @Override
    public Syntax decomposed()
    {
        return new MethodDeclarationSyntax(arguments.flow().map(a -> a.decomposed()).collect(), expression.decomposed());
    }

    @Override
    public Value value(Namespace namespace)
    {
        return new AnonymousMethodValue(arguments.length(), ps ->
        {
            return expression.value(new PairNamespace(arguments.flow().<Source>map(SyntaxSource::new).zip(ps.flow()).collect()));
        });
    }
}