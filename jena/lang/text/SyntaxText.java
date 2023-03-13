package jena.lang.text;

import jena.lang.syntax.Syntax;

public final class SyntaxText implements Text
{
    private Text text;

    public SyntaxText(Syntax syntax)
    {
        StringBuilder sb = new StringBuilder();
        syntax.text(text -> sb.append(text));
        text = new StringText(sb.toString());
    }

    @Override
    public char at(int index)
    {
        return text.at(index);
    }

    @Override
    public int length()
    {
        return text.length();
    }
}