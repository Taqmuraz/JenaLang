package jena.lang.source;

import java.util.ArrayList;
import java.util.List;

import jena.lang.MaxCount;
import jena.lang.StartPosition;

public final class SourceCharacterBuffer implements CharacterBuffer
{
    List<Character> buffer;

    public SourceCharacterBuffer(Source source)
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

    @Override
    public String toString()
    {
        char[] text = new char[buffer.size()];
        for(int i = 0; i < text.length; i++) text[i] = buffer.get(i);
        return String.valueOf(text);
    }
}