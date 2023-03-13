package jena.lang.value;

import jena.lang.text.Text;

public final class SingleNamespace implements Namespace
{
    private Text name;
    private Value value;
    
    public SingleNamespace(Text name, Value value)
    {
        this.name = name;
        this.value = value;
    }

    @Override
    public Value name(Text name)
    {
        if(name.compare(this.name)) return value;
        return NoneValue.instance;
    }
}