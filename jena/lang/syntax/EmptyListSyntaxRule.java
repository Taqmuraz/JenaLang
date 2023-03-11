package jena.lang.syntax;

import jena.lang.EmptyGenericBuffer;
import jena.lang.source.SourceSpan;

public final class EmptyListSyntaxRule implements SyntaxListRule
{
    @Override
    public void match(SourceSpan span, SyntaxListSpanAction action)
    {
        action.call(new EmptyGenericBuffer<Syntax>(), span);
    }
}