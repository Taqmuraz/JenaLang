package jena.lang.syntax;

import jena.lang.Result;
import jena.lang.source.JenaSourceFlow;
import jena.lang.source.Source;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;
import jena.lang.value.Namespace;
import jena.lang.value.ValueFunction;

public interface Syntax
{
    void text(TextWriter writer);
    ValueFunction value(Namespace namespace);

    static Result<Syntax, SyntaxMistake> read(Source source)
    {
        if(source.isEmpty()) return Result.result(new NoneValueSyntax());
        var match = SyntaxRule.any().match(new JenaSourceFlow(source).span());
        
        if(match.present())
        {
            var pair = match.item().pair();
            var syntax = pair.a;
            var span = pair.b;
            if(span.at(0).isEmpty())
            {
                return Result.result(syntax);
            }
            else
            {
                return Result.none(SyntaxMistake.of(Text.of("Syntax tree is not complete"))
                    .located(span.at(0).location())
                    .withSource(span.at(0)));
            }
        }
        else
        {
            return Result.none(SyntaxMistake.of(Text.of("no syntax")));
        }
    }
}