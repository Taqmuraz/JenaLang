package jena.lang.value;

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
    public ValueFunction name(Text name)
    {
        if(name.compare(this.name)) return value;
        return ValueFunction.none;
    }
}