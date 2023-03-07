package jena.lang.value;

import jena.lang.source.Source;

public final class NestedNamespace implements Namespace
{
    private Namespace outer;
    private Namespace inner;

    public NestedNamespace(Namespace outer, Namespace inner) {
        this.outer = outer;
        this.inner = inner;
    }

    @Override
    public Value name(Source name)
    {
        Value innerName = inner.name(name);
        if(innerName instanceof NoneValue) return outer.name(name);
        return innerName;
    }
}