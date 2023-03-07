package jena.lang.value;

import jena.lang.GenericBuffer;
import jena.lang.GenericFlow;
import jena.lang.GenericPair;
import jena.lang.source.Source;

public final class FlowNamespace implements Namespace
{
    GenericBuffer<GenericPair<Source, Value>> names;

    public FlowNamespace(GenericFlow<GenericPair<Source, Value>> flow)
    {
        names = flow.collect();
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