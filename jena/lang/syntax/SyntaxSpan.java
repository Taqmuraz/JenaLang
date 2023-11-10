package jena.lang.syntax;

import jena.lang.StructPair;
import jena.lang.source.SourceSpan;

public interface SyntaxSpan
{
    interface Mapping
    {
        SyntaxSpan map(Syntax syntax, SourceSpan span);
    }

    void accept(SyntaxSpanAction action);

    default StructPair<Syntax, SourceSpan> pair()
    {
        return new StructPair<Syntax, SourceSpan>(action -> accept(action::call));
    }

    static SyntaxSpan of(Syntax syntax, SourceSpan span)
    {
        return action -> action.call(syntax, span);
    }
    default SyntaxSpan map(Mapping mapping)
    {
        return action -> accept((syntax, span) -> mapping.map(syntax, span).accept(action));
    }
}