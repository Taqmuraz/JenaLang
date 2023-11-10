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
            boolean[] noMatch = { false };
            SourceSpan[] start = { span };
            ArrayList<Syntax> elements = new ArrayList<>();
            do
            {
                var result = SyntaxRule.any().match(span);
                result.ifPresentElse(item ->
                {
                    var pair = item.pair();
                    elements.add(pair.a);
                    start[0] = pair.b;
                },
                () -> noMatch[0] = true);
                
                if(!start[0].at(0).text().compare(Text.of(',')))
                {
                    break;
                }
            }
            while(!noMatch[0] && !start[0].at(0).isEmpty());
            if(start[0].at(0).text().compare(close))
            {
                return Optional.yes(SyntaxSpan.of(new ExpressionListSyntax(
                    GenericBuffer.of(elements),
                    open,
                    close,
                    factory), start[0].skip(1)));
            }
        }
        return Optional.no();
    }
}