package jena.syntax;

import jena.lang.source.SingleCharacterSource;
import jena.lang.source.Source;

public final class MemberAccessExpressionSyntax implements Syntax
{
    private Syntax expression;
    private Source memberName;

    public MemberAccessExpressionSyntax(Syntax expression, Source memberName)
    {
        this.expression = expression;
        this.memberName = memberName;
    }

    @Override
    public void source(SyntaxSerializer writer)
    {
        expression.source(writer);
        writer.source(new SingleCharacterSource('.'));
        writer.source(memberName);
    }
}