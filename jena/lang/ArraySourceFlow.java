package jena.lang;

import java.util.stream.IntStream;

public final class ArraySourceFlow implements SourceFlow
{
    private Source[] sources;
    private int position;

    public ArraySourceFlow(Source... sources)
    {
        this.sources = sources;
    }

    @Override
    public void read(Count count, SourceFlowReader reader)
    {
        int c = count.count(sources.length - position);
        IntStream.range(position, position + c).boxed().map(i -> sources[i]).forEach(reader::call);
    }
}