package jena.lang.value;

import jena.lang.Optional;
import jena.lang.text.Text;

public final class SingleNamespace implements Namespace
{
    private Text name;
    private ValueFunction value;
    
    public SingleNamespace(Text name, ValueFunction value)
    {
        this.name = name;
        this.value = value;
    }

    @Override
    public Optional<ValueFunction> name(Text name)
    {
        if(name.compare(this.name)) return Optional.yes(value);
        return Optional.no();
    }
}