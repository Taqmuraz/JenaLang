package jena.lang.syntax;

import jena.lang.Result;
import jena.lang.source.JenaSourceFlow;
import jena.lang.source.Source;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.ValueProducer;

public interface Syntax extends ValueProducer
{
    void text(TextWriter writer);

    static Result<Syntax, SyntaxMistake> read(Source source)
    {
        var match = SyntaxRule.any().match(new JenaSourceFlow(source).span());
        return (result, none) ->
        {
            match.ifPresentElse(ss -> ss.accept((syntax, span) ->
            {
                if(span.at(0).isEmpty())
                    result.call(syntax);
                else none.call(SyntaxMistake.of(Text.of("Syntax tree is not complete"))
                    .located(span.at(0).location())
                    .withSource(span.at(0)));
            }), () ->
            {
                none.call(SyntaxMistake.of(Text.of("no syntax")));
            });
        };
    }
}