package jena.lang.value;

import java.util.HashMap;
import java.util.Map;

import jena.lang.GenericBuffer;
import jena.lang.GenericPair;
import jena.lang.Optional;
import jena.lang.text.Text;

public final class HashMapNamespace implements Namespace
{
    private Map<String, Value> names = new HashMap<String, Value>();

    public HashMapNamespace(GenericBuffer<GenericPair<Text, Value>> names)
    {
        names.flow().read(p -> p.both((n, v) -> this.names.put(n.string(), v)));
    }
    public HashMapNamespace(SymbolValuePair pairs)
    {
        pairs.use((t, v) -> names.put(t, v.call()));
    }

    @Override
    public Optional<ValueFunction> name(Text name)
    {
        Value value = names.get(name.string());
        if (value == null) return Optional.no();
        return Optional.yes(ValueFunction.of(value));
    }
}