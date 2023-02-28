package jena.lang;

import java.util.ArrayList;
import java.util.List;

public final class StringLiteralFlow implements SourceFlow
{
    private SourceFlow flow;

    public StringLiteralFlow(String source)
    {
        List<SourceFlow> flows = new ArrayList<SourceFlow>();
        StringBuilder text = new StringBuilder();
        boolean literal = false;

        for(int i = 0; i < source.length(); i++)
        {
            char c = source.charAt(i);
            if (c == '\"')
            {
                Source s = new StringSource(text.toString());
                if (!literal) flows.add(new CharacterSeparatedSourceFlow(Character::isWhitespace, s));
                else flows.add(new SingleSourceFlow(s));
                text = new StringBuilder();
                literal = !literal;
            }
            else
            {
                text.append(c);
            }
        }

        this.flow = new CompositeSourceFlow(flows);
    }

    @Override
    public void read(Count count, SourceFlowReader reader)
    {
        flow.read(count, reader);
    }
}