package jena.lang.syntax;

import jena.lang.EmptyBuffer;
import jena.lang.source.SourceSpan;

public final class EmptyListSyntaxRule implements SyntaxListRule
{
    @Override
    public void match(SourceSpan span, SyntaxListSpanAction action, SyntaxMistakeSpanAction mistakeAction)
    {
        action.call(new EmptyBuffer<Syntax>(), span);
    }
}