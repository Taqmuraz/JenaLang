package jena.lang.value;

import java.util.HashMap;
import java.util.Map;

import jena.lang.GenericBuffer;
import jena.lang.GenericPair;
import jena.lang.source.Source;

public final class HashMapNamespace implements Namespace
{
    private Map<String, Value> names = new HashMap<String, Value>();

    public HashMapNamespace(GenericBuffer<GenericPair<Source, Value>> names)
    {
        names.flow().read(p -> p.both((n, v) -> this.names.put(n.text().toString(), v)));
    }

    @Override
    public Value name(Source name)
    {
        Value value = names.get(name.text().toString());
        if (value == null) return NoneValue.instance;
        return value;
    }
}