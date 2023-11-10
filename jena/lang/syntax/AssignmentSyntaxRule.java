package jena.lang.syntax;

import jena.lang.Optional;
import jena.lang.source.SourceSpan;
import jena.lang.text.Text;

public final class AssignmentSyntaxRule implements SyntaxRule
{
    @Override
    public Optional<SyntaxSpan> match(SourceSpan span)
    {
        return SyntaxRule.name.match(span).mapOptional(name ->
        {
            var namePair = name.pair();
            if(namePair.b.at(0).text().compare(Text.of('=')))
            {
                return SyntaxRule.any().match(namePair.b.skip(1)).mapOptional(expression ->
                {
                    var exPair = expression.pair();
                    if(exPair.b.at(0).text().compare(Text.of("->")))
                    {
                        return SyntaxRule.any().match(exPair.b.skip(1)).mapOptional(next ->
                        {
                            var nextPair = next.pair();
                            return Optional.yes(SyntaxSpan.of(new AssignmentSyntax(
                                Text.of(namePair.a),
                                exPair.a,
                                nextPair.a), nextPair.b));
                        });
                    }
                    return Optional.no();
                });
            }
            return Optional.no();
        });
    }
}