package jena.lang.syntax;

import jena.lang.source.Source;
import jena.lang.source.SourceFlow;
import jena.lang.source.JenaSourceFlow;

public class JenaSyntaxReader
{
    private SyntaxRule rule = new AnyExpressionSyntaxRule();
    private SourceFlow flow;
    
    public JenaSyntaxReader(Source source)
    {
        flow = new JenaSourceFlow(source);
    }

    public void read(SyntaxAction action, SyntaxMistakeAction mistakeAction)
    {
        boolean[] hasSyntax = { false };
        new SyntaxGuess(flow.span(), rule).guess((syntax, span) ->
        {
            if(span.at(0).isEmpty())
            {
                hasSyntax[0] = true;
                action.call(syntax.decomposed());
            }
        }, (mistake, span) ->
        {
            if(!hasSyntax[0]) mistakeAction.call(new LocatedSyntaxMistake(mistake, span.at(0).location(0)));
        });
    }
}