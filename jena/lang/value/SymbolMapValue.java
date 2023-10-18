package jena.lang.value;

import java.util.HashMap;

import jena.lang.GenericFlow;
import jena.lang.IterableFlow;
import jena.lang.text.StringText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public class SymbolMapValue implements Value
{
    HashMap<String, ValueFunction> map = new HashMap<>();
    ValueCallFunction defaultValue;

    public SymbolMapValue(SymbolValuePair pairs, ValueCallFunction defaultValue)
    {
        pairs.use((t, v) -> map.put(t, v));
        this.defaultValue = defaultValue;
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write(new StringText("{"));
        new IterableFlow<>(map.entrySet()).<GenericFlow<Text>>map(e -> f ->
        {
            f.call(new StringText(e.getKey()));
            f.call(new StringText(":"));
            e.getValue().call().print(f::call);
        })
        .join(e -> e.read(writer::write), () -> writer.write(new StringText(", ")));
        writer.write(new StringText("}"));
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