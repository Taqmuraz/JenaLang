package jena.lang.syntax;

import jena.lang.GenericBuffer;
import jena.lang.source.SingleCharacterSource;
import jena.lang.source.Source;
import jena.lang.source.StringSource;
import jena.lang.value.FlowNamespace;
import jena.lang.value.MethodValue;
import jena.lang.value.Namespace;
import jena.lang.value.NoneValue;
import jena.lang.value.Value;

public final class MethodDeclarationSyntax implements Syntax
{
    private Source name;
    private GenericBuffer<Syntax> arguments;
    private Syntax expression;
    
    public MethodDeclarationSyntax(Source name, GenericBuffer<Syntax> arguments, Syntax expression)
    {
        this.name = name;
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
        return new MethodDeclarationSyntax(name, arguments.flow().map(a -> a.decomposed()).collect(), expression.decomposed());
    }

    @Override
    public Value value(Namespace namespace)
    {
        return new MethodValue(name, args -> args.number(arguments.length(), ps ->
        {
            return expression.value(new FlowNamespace(arguments.flow().<Source>map(SyntaxSource::new).zip(ps.flow())));
        }, () -> NoneValue.instance));
    }
}