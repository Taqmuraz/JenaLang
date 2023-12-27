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
        if(index < 0 || index >= buffer.length) return Source.empty();
        return buffer[index];
    }

    private SourceSpan span(int start)
    {
        return new SourceSpan()
        {
            @Override
            public Source at(int index)
            {
                int i = start + index;
                if(i < buffer.length) return buffer[i];
                else
                {
                    SourceLocation location = buffer.length == 0 ? new ZeroSourceLocation() : buffer[buffer.length - 1].location();
                    return new EmptySource(location);
                }
            }

            @Override
            public SourceSpan skip(int count)
            {
                return span(start + count);
            }

            @Override
            public int code()
            {
                return start;
            }
        };
    }

    @Override
    public SourceSpan skip(int count)
    {
        return span(count);
    }

    @Override
    public int code()
    {
        return 0;
    }
}