package jena.lang.syntax;

import jena.lang.source.Source;
import jena.lang.source.SourceFlow;
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
        match.ifPresentElse(ss -> ss.accept((syntax, span) -> action.call(syntax)), () ->
        {
            System.out.println("no syntax");
        });
    }
}