package jena.lang.value;

import java.util.HashMap;

import jena.lang.IterableFlow;
import jena.lang.text.StringText;
import jena.lang.text.TextWriter;

public class SymbolMatchValue implements Value
{
    HashMap<String, ValueFunction> map = new HashMap<>();
    ValueCallFunction defaultValue;

    public SymbolMatchValue(SymbolValuePair pairs, ValueCallFunction defaultValue)
    {
        pairs.use((t, v) -> map.put(t, v));
        this.defaultValue = defaultValue;
    }

    @Override
    public void print(TextWriter writer)
    {
        new IterableFlow<>(map.entrySet()).read(e ->
        {
            writer.write(new StringText("{"));
            writer.write(new StringText(e.getKey()));
            writer.write(new StringText(":"));
            e.getValue().call().print(writer);
            writer.write(new StringText("}"));
        });
    }

    @Override
    public Value call(Value argument)
    {
        if(argument instanceof SymbolValue symbol)
        {
            String key = symbol.name.string();
            if (map.containsKey(key)) return map.get(key).call();
        }
        return defaultValue.call(argument);
    }

    @Override
    public boolean valueEquals(Value v)
    {
        return v == this;
    }
}