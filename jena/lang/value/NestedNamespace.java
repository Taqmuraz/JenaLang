package jena.lang.value;

import jena.lang.text.Text;

public final class NestedNamespace implements Namespace
{
    private Namespace outer;
    private Namespace inner;

    public NestedNamespace(Namespace outer, Namespace inner) {
        this.outer = outer;
        this.inner = inner;
    }

    @Override
    public ValueFunction name(Text name)
    {
        ValueFunction innerName = inner.name(name);
        if(innerName == ValueFunction.none) return outer.name(name);
        return innerName;
    }
}