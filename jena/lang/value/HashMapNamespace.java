package jena.lang.value;

import java.util.HashMap;
import java.util.Map;

import jena.lang.source.Source;

public final class HashMapNamespace implements Namespace
{
    private Map<String, Value> names = new HashMap<String, Value>();

    public void addName(Source name, Value value)
    {
        names.put(name.text().toString(), value);
    }

    @Override
    public Value name(Source name)
    {
        return names.get(name.text().toString());
    }
}