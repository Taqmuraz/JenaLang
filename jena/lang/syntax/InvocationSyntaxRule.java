package jena.lang.syntax;

import jena.lang.Optional;
import jena.lang.source.SourceSpan;

public class InvocationSyntaxRule implements SyntaxRule
{
    @Override
    public Optional<SyntaxSpan> match(SourceSpan span)
    {
        return SyntaxRule.lower().match(span).mapOptional(ex ->
        {
            var exPair = ex.pair();
            return SyntaxRule.any().match(exPair.b).mapOptional(arg ->
            {
                var argPair = arg.pair();
                return Optional.yes(SyntaxSpan.of(new InvocationSyntax(exPair.a, argPair.a), argPair.b));
            })
            .orElse(ex);
        });
    }

    SyntaxRule continued(Syntax from)
    {
        return span ->
        {
            return SyntaxRule.lower().match(span).mapOptional(arg ->
            {
                var argPair = arg.pair();
                return Optional.yes(SyntaxSpan.of(new InvocationSyntax(from, argPair.a), argPair.b));
            })
            .orElse(SyntaxSpan.of(from, span));
        };
    }
}