package jena.syntax;

import jena.lang.source.SingleCharacterSource;

public class AddExpressionSyntax implements Syntax
{
    private Syntax left;
    private Syntax right;
    
    public AddExpressionSyntax(Syntax left, Syntax right)
    {
        this.left = left;
        this.right = right;
    }

    @Override
    public void source(SyntaxSerializer writer)
    {
        left.source(writer);
        writer.source(new SingleCharacterSource('+'));
        right.source(writer);
    }
}