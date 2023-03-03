package jena.lang.source;

import jena.lang.MaxCount;
import jena.lang.StartPosition;

public final class EmptySourceFilter implements SourceFilter
{
    private class EmptySourceCheck
    {
        EmptySourceCheck(Source source)
        {
            source.read(StartPosition.instance, MaxCount.instance, (c, n) -> count++);
        }

        private int count;

        boolean empty()
        {
            return count == 0;
        }
    }

    @Override
    public boolean check(Source source)
    {
        return this.new EmptySourceCheck(source).empty();
    }
}