package jena.lang.source;

import java.io.File;

import jena.lang.Count;
import jena.lang.OneCount;
import jena.lang.Position;
import jena.lang.StartPosition;
import jena.lang.text.SourceText;
import jena.lang.text.Text;

public interface Source
{
    void read(Position position, Count count, SourceSymbolAction buffer);
    SourceLocation location();

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

    default boolean isEmpty()
    {
        boolean[] empty = {true};
        read(StartPosition.instance, OneCount.instance, (c, n) -> empty[0] = false);
        return empty[0];
    }

    default Text text()
    {
        return new SourceText(this);
    }

    static Source of(File file)
    {
        return new FileSource(file);
    }
    static Source of(String name, String source)
    {
        return new StringSource(Text.of(name), source);
    }
}