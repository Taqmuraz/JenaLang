package jena.syntax;

import jena.lang.source.Source;

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
}