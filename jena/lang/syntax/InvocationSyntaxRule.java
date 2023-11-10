package jena.lang.syntax;

import java.util.ArrayList;

import jena.lang.Optional;
import jena.lang.source.SourceSpan;

public class InvocationSyntaxRule implements SyntaxRule
{
    @Override
    public Optional<SyntaxSpan> match(SourceSpan span)
    {
        var matched = new ArrayList<Syntax>();
        SourceSpan[] start = { span };
        boolean[] noMatch = { false };
        do
        {
            SyntaxRule.lower().match(start[0]).ifPresentElse(item ->
            {
                var pair = item.pair();
                start[0] = pair.b;
                matched.add(pair.a);
            },
            () -> noMatch[0] = true);
        } while(!noMatch[0] && !start[0].at(0).isEmpty());

        if(matched.isEmpty()) return Optional.no();

        Syntax result = matched.get(0);
        for(int i = 1; i < matched.size(); i++)
        {
            result = new InvocationSyntax(result, matched.get(i));
        }
        return Optional.yes(SyntaxSpan.of(result, start[0]));
    }
}