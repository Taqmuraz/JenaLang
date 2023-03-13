package jena.lang.value;

import jena.lang.GenericBuffer;
import jena.lang.GenericPair;
import jena.lang.text.Text;

public final class PairNamespace implements Namespace
{
    GenericBuffer<GenericPair<Text, Value>> names;

    public PairNamespace(GenericBuffer<GenericPair<Text, Value>> names)
    {
        this.names = names;
    }

    @Override
    public Value name(Text name)
    {
        Value[] value = { NoneValue.instance };
        for(int i = 0; i < names.length(); i++)
        {
            names.at(i).both((n, v) ->
            {
                if(n.compare(name))
                {
                    value[0] = v;
                }
            });
        }
        return value[0];
    }
}