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

    public void read(SyntaxAction action)
    {
        new SyntaxGuess(flow.span(), rule).guess((syntax, s) -> action.call(syntax));
    }
}