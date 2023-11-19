package jena.lang.value;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import jena.lang.GenericBuffer;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public final class MapValue implements Value
{
    class Key
    {
        Value value;

        public Key(Value value)
        {
            this.value = value;
        }
        @Override
        public boolean equals(Object obj)
        {
            return obj instanceof Key k && k.value.valueEquals(value);
        }
        @Override
        public int hashCode()
        {
            return value.valueCode();
        }
    }
    Map<Key, Value> map = new HashMap<Key, Value>();
    GenericBuffer<Value> pairs;
    static final SymbolValue keySymbol = new SymbolValue(Text.of("key"));
    static final SymbolValue valueSymbol = new SymbolValue(Text.of("value"));

    public MapValue(GenericBuffer<Value> pairs)
    {
        this.pairs = pairs;
        pairs.each(p ->
        {
            map.put(new Key(p.call(keySymbol)), p.call(valueSymbol));
        });
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write("{");
        pairs.flow().join(p -> p.print(writer), () -> writer.write(","));
        writer.write("}");
    }

    @Override
    public Value call(Value argument)
    {
        var item = map.get(new Key(argument));
        if(item == null) return NoneValue.instance;
        return item;
    }

    @Override
    public boolean valueEquals(Value v)
    {
        return v == this;
    }

    @Override
    public int valueCode()
    {
        return hashCode();
    }

    @Override
    public Object toObject(Class<?> type)
    {
        if(type.isAssignableFrom(HashMap.class))
        {
            var map = new HashMap<Object, Object>();
            for(var p : this.map.entrySet())
            {
                map.put(p.getKey().value.toObject(Object.class), p.getValue().toObject(Object.class));
            }
            return map;
        }
        else if(type.isInterface())
        {
            return Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] { type }, (p, method, args) ->
            {
                var value = map.get(new Key(new SymbolValue(Text.of(method.getName()))));
                return FunctionValue.callMethod(args, method.getReturnType(), value::call);
            });
        }
        else throw new RuntimeException(String.format("%s is expected to be a HashMap assignable class or interface", type.getName()));
    }
}