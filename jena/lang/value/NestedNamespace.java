package jena.lang.value;

import jena.lang.Optional;
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
    public Optional<ValueFunction> name(Text name)
    {
        var innerName = inner.name(name);
        if(!innerName.present()) return outer.name(name);
        return innerName;
    }
}