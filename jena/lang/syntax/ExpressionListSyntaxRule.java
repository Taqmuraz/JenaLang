package jena.lang.syntax;

import java.util.ArrayList;

import jena.lang.GenericBuffer;
import jena.lang.Optional;
import jena.lang.source.SourceSpan;
import jena.lang.syntax.ExpressionListSyntax.ValueFactory;
import jena.lang.text.Text;

public class ExpressionListSyntaxRule implements SyntaxRule
{
    Text open;
    Text close;
    ExpressionListSyntax.ValueFactory factory;

    public ExpressionListSyntaxRule(Text open, Text close, ValueFactory factory)
    {
        this.open = open;
        this.close = close;
        this.factory = factory;
    }

    @Override
    public Optional<SyntaxSpan> match(SourceSpan span)
    {
        if(span.at(0).text().compare(open))
        {
            boolean noMatch = false;
            SourceSpan start = span.skip(1);
            ArrayList<Syntax> elements = new ArrayList<>();
            do
            {
                if(start.at(0).text().compare(Text.of(',')))
                {
                    start = start.skip(1);
                    continue;
                }

                var result = SyntaxRule.any().match(start);
                if(result.present())
                {
                    var pair = result.item().pair();
                    elements.add(pair.a);
                    start = pair.b;
                }
                else noMatch = true;
            }
            while(!noMatch && !start.at(0).isEmpty());
            if(start.at(0).text().compare(close))
            {
                return Optional.yes(SyntaxSpan.of(new ExpressionListSyntax(
                    GenericBuffer.of(elements),
                    open,
                    close,
                    factory), start.skip(1)));
            }
        }
        return Optional.no();
    }
}