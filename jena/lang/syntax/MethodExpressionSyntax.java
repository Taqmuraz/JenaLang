package jena.lang.syntax;

import jena.lang.GenericBuffer;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.StringText;
import jena.lang.text.SyntaxText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.PairNamespace;
import jena.lang.value.AnonymousMethodValue;
import jena.lang.value.Namespace;
import jena.lang.value.Value;

public final class MethodExpressionSyntax implements Syntax
{
    private GenericBuffer<Syntax> arguments;
    private Syntax expression;
    
    public MethodExpressionSyntax(GenericBuffer<Syntax> arguments, Syntax expression)
    {
        this.arguments = arguments;
        this.expression = expression;
    }

    @Override
    public void text(TextWriter writer)
    {
        writer.write(new StringText("method"));
        writer.write(new SingleCharacterText('('));
        arguments.flow().join(a -> a.text(writer), () -> writer.write(new SingleCharacterText(',')));
        writer.write(new SingleCharacterText(')'));
        expression.text(writer);
    }

    @Override
    public Syntax decomposed()
    {
        return new MethodExpressionSyntax(arguments.flow().map(a -> a.decomposed()).collect(), expression.decomposed());
    }

    @Override
    public Value value(Namespace namespace)
    {
        return new AnonymousMethodValue(arguments.map(SyntaxText::new), ps ->
        {
            return expression.value(namespace.nested(new PairNamespace(arguments.flow().<Text>map(SyntaxText::new).zip(ps.flow()).collect())));
        });
    }
}