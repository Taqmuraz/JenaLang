package jena.lang.value;

import jena.lang.text.Text;

public final class EmptyNamespace implements Namespace
{
    public final static Namespace instance = new EmptyNamespace();

    @Override
    public ValueFunction name(Text name)
    {
        return ValueFunction.none;
    }
}