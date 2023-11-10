package jena.lang.syntax;

import jena.lang.source.SourceSpan;

public interface SyntaxSpan
{
    interface Mapping
    {
        SyntaxSpan map(Syntax syntax, SourceSpan span);
    }

    void accept(SyntaxSpanAction action);

    static SyntaxSpan of(Syntax syntax, SourceSpan span)
    {
        return action -> action.call(syntax, span);
    }
    default SyntaxSpan map(Mapping mapping)
    {
        return action -> accept((syntax, span) -> mapping.map(syntax, span).accept(action));
    }
}