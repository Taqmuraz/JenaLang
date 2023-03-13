package jena.lang.value;

import jena.lang.text.Text;

public interface Namespace
{
    Value name(Text name);

    default Namespace nested(Namespace inner)
    {
        return new NestedNamespace(this, inner);
    }
}