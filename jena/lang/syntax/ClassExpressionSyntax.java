package jena.lang.syntax;

import jena.lang.GenericBuffer;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.StringText;
import jena.lang.text.SyntaxText;
import jena.lang.text.TextWriter;
import jena.lang.value.ClassValue;
import jena.lang.value.Namespace;
import jena.lang.value.Value;

public final class ClassExpressionSyntax implements Syntax
{
    private GenericBuffer<Syntax> arguments;
    private GenericBuffer<Syntax> members;

    public ClassExpressionSyntax(GenericBuffer<Syntax> arguments, GenericBuffer<Syntax> members)
    {
        this.arguments = arguments;
        this.members = members;
    }
    @Override
    public void text(TextWriter writer)
    {
        writer.write(new StringText("class"));
        ExpressionListWriter listWriter = new ExpressionListWriter(new SingleCharacterText('('), new SingleCharacterText(')'));
        listWriter.write(arguments, writer);
        listWriter.write(members, writer);
    }
    @Override
    public Syntax decomposed()
    {
        return new ClassExpressionSyntax(
            arguments.flow().map(arg -> arg.decomposed()).collect(),
            members.flow().map(m -> m.decomposed()).collect());
    }
    @Override
    public Value value(Namespace namespace)
    {
        return new ClassValue(arguments.map(SyntaxText::new), members.flow().map(m -> ((PairExpressionSyntax)m).nameValue()).collect(), namespace);
    }
}