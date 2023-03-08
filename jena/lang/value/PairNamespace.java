package jena.lang.value;

import jena.lang.GenericBuffer;
import jena.lang.GenericPair;
import jena.lang.source.Source;

public final class PairNamespace implements Namespace
{
    GenericBuffer<GenericPair<Source, Value>> names;

    public PairNamespace(GenericBuffer<GenericPair<Source, Value>> names)
    {
        this.names = names;
    }

    @Override
    public Value name(Source name)
    {
        Value[] value = { NoneValue.instance };
        for(int i = 0; i < names.length(); i++)
        {
            names.at(i).both((n, v) ->
            {
                if(n.text().compare(name.text()))
                {
                    value[0] = v;
                }
            });
        }
        return value[0];
    }
}