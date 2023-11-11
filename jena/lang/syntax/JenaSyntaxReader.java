package jena.lang.syntax;

import jena.lang.source.Source;
import jena.lang.source.SourceFlow;
import jena.lang.text.Text;
import jena.lang.source.JenaSourceFlow;

public class JenaSyntaxReader
{
    private SourceFlow flow;
    
    public JenaSyntaxReader(Source source)
    {
        flow = new JenaSourceFlow(source);
    }

    public void read(SyntaxAction action, SyntaxMistakeAction mistakeAction)
    {
        var match = SyntaxRule.any().match(flow.span());
        match.ifPresentElse(ss -> ss.accept((syntax, span) ->
        {
            if(span.at(0).isEmpty())action.call(syntax);
            else mistakeAction.call(SyntaxMistake.of(Text.of("Syntax tree is not complete"))
                .located(span.at(0).location())
                .withSource(span.at(0)));
        }), () ->
        {
            System.out.println("no syntax");
        });
    }
}