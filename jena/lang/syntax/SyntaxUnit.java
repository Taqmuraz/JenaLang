package jena.lang.syntax;

import jena.lang.text.Text;

public interface SyntaxUnit
{
    Syntax complete();

    static SyntaxUnit of(Syntax syntax)
    {
        return new SyntaxUnit()
        {
            @Override
            public Syntax complete()
            {
                return syntax;
            }
            @Override
            public String toString()
            {
                return Text.of(syntax).string();
            }
        };
    }
}