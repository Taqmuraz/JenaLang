package jena.lang.source;

import java.util.ArrayList;
import java.util.List;

public class SourceSpanFromFlow implements SourceSpan
{
    Source[] buffer;

    public SourceSpanFromFlow(SourceFlow flow)
    {
        List<Source> list = new ArrayList<Source>();
        flow.read(list::add);
        buffer = list.toArray(Source[]::new);
    }

    @Override
    public Source at(int index)
    {
        return buffer[index];
    }

    private SourceSpan span(int start)
    {
        return new SourceSpan()
        {
            @Override
            public Source at(int index)
            {
                return buffer[start + index];
            }

            @Override
            public SourceSpan skip(int count)
            {
                return span(start + count);
            }
        };
    }

    @Override
    public SourceSpan skip(int count)
    {
        return span(count);
    }
}