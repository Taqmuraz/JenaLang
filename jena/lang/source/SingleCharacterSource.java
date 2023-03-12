package jena.lang.source;

import jena.lang.Count;
import jena.lang.Position;

public final class SingleCharacterSource implements Source
{
    private char character;

    public SingleCharacterSource(char character) {
        this.character = character;
    }

    @Override
    public void read(Position position, Count count, SourceSymbolAction buffer)
    {
        int c = count.count(1);
        int start = position.position(0, 1);
        if(c >= 1 && start == 0) buffer.call(character, 0);
    }

    @Override
    public SourceLocation location(int position)
    {
        if(position == 0) return new FixedSourceLocation(0, 0);
        else return new WrongSourceLocation();
    }
}