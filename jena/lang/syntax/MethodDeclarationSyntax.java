package jena.lang.syntax;

import jena.lang.GenericBuffer;
import jena.lang.GenericFlow;
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
    private GenericFlow<Syntax> arguments;
    private Syntax expression;
    
    public MethodDeclarationSyntax(Source name, GenericFlow<Syntax> arguments, Syntax expression)
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
        arguments.join(a -> a.source(writer), () -> writer.source(new SingleCharacterSource(' ')));
        writer.source(new SingleCharacterSource(')'));
        expression.source(writer);
    }

    @Override
    public Syntax decomposed()
    {
        return new MethodDeclarationSyntax(name, arguments.map(a -> a.decomposed()), expression.decomposed());
    }

    @Override
    public Value value(Namespace namespace)
    {
        GenericBuffer<Syntax> params = arguments.collect();
        return new MethodValue(name, args -> args.number(params.length(), ps ->
        {
            return expression.value(new FlowNamespace(arguments.<Source>map(SyntaxSource::new).zip(ps.flow())));
        }, () -> NoneValue.instance));
    }
}