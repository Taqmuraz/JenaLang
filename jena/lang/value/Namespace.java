package jena.lang.value;

import jena.lang.text.Text;

public interface Namespace
{
    ValueFunction name(Text name);

    default Namespace nested(Namespace inner)
    {
        return new NestedNamespace(this, inner);
    }

    static final Namespace standard = new StorageNamespace(
        new IONamespace().nested(
            new SwingNamespace().nested(
                JavaNamespace.create())));
}