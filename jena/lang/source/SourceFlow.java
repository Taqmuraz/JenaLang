package jena.lang.source;

public interface SourceFlow
{
    void read(SourceAction reader);

    default SourceFlow filter(SourceFilter filter)
    {
        return new FilterSourceFlow(this, filter);
    }
    default SourceFlow notFilter(SourceFilter filter)
    {
        return new FilterSourceFlow(this, s -> !filter.check(s));
    }

    default SourceFlow kindFilter(CharacterKind kind)
    {
        return new FilterSourceFlow(this, source -> source.isKind(kind));
    }

    default SourceFlow notKindFilter(CharacterKind kind)
    {
        return new FilterSourceFlow(this, source -> !source.isKind(kind));
    }

    default SourceFlow split(CharacterKind kind)
    {
        return new FlatMapSourceFlow(this, s -> s.split(kind));
    }

    default SourceFlow map(SourceMapping mapping)
    {
        return new MapSourceFlow(this, mapping);
    }

    default SourceSpan span()
    {
        return new SourceSpanFromFlow(this);
    }
}