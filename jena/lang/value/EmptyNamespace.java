package jena.lang.value;

import jena.lang.Optional;
import jena.lang.text.Text;

public final class EmptyNamespace implements Namespace
{
    public final static Namespace instance = new EmptyNamespace();

    @Override
    public Optional<ValueFunction> name(Text name)
    {
        return Optional.no();
    }
}