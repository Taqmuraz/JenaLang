package jena.lang.source;

import jena.lang.Count;
import jena.lang.Position;

public interface Source
{
    void read(Position position, Count count, SourceSymbolAction buffer);
    SourceLocation location(int position);

    default Source subsource(int position, int count)
    {
        return new RelativeSource(this, position, count);
    }

    default SourceFlow split(CharacterKind kind)
    {
        return flow(s -> new SingleCharacterSeparatedSourceFlow(s, kind));
    }

    default SourceFlow flow(SourceFlatMapping mapping)
    {
        return mapping.map(this);
    }

    default SourceFlow single()
    {
        return new SingleSourceFlow(this);
    }

    default boolean isKind(CharacterKind kind)
    {
        return new SourceCharacterKindCheck(this, kind).isKind();
    }

    default CharacterBuffer text()
    {
        return new SourceCharacterBuffer(this);
    }
}