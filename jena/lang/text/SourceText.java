package jena.lang.text;

import java.util.ArrayList;
import java.util.List;

import jena.lang.MaxCount;
import jena.lang.StartPosition;
import jena.lang.source.Source;

public final class SourceText implements Text
{
    private List<Character> buffer;

    public SourceText(Source source)
    {
        buffer = new ArrayList<Character>();
        source.read(StartPosition.instance, MaxCount.instance, (c, n) -> buffer.add(c));
    }

    @Override
    public char at(int index)
    {
        return buffer.get(index);
    }

    @Override
    public int length()
    {
        return buffer.size();
    }
}