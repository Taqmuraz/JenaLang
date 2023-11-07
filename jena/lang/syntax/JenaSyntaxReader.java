package jena.lang.syntax;

import jena.lang.source.Source;
import jena.lang.source.SourceFlow;
import jena.lang.source.SourceSpan;

import jena.lang.Action;
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
        Action mismatch = () ->
        {
            mistakeAction.call(writer -> writer.write("No syntax matched"));
        };
        SourceSpan start = flow.span();
        var stack = SyntaxListStack.make(SyntaxList.invocationList());
        SyntaxStackRule.matchStack(stack, start, mismatch);
        SyntaxList list;
        do
        {
            list = stack.pop();
        }
        while(!stack.isEmpty());
        action.call(list.complete());
    }
}