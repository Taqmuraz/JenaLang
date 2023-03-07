package jena.lang.value;

import jena.lang.source.Source;

public interface Namespace
{
    Value name(Source name);

    default Namespace nested(Namespace inner)
    {
        return new NestedNamespace(this, inner);
    }
}