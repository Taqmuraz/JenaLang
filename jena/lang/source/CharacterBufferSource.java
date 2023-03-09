package jena.lang.source;

import jena.lang.Count;
import jena.lang.Position;

public final class CharacterBufferSource implements Source
{
    private CharacterBuffer buffer;

    public CharacterBufferSource(CharacterBuffer buffer)
    {
        this.buffer = buffer;
    }

    @Override
    public void read(Position position, Count count, SourceSymbolAction action)
    {
        int c = count.count(buffer.length());
        int p = position.position(0, c);
        for(int i = 0; i < c; i++) action.call(buffer.at(i + p), i);
    }
}