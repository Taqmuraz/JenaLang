package jena.lang.value;

import jena.lang.ArrayBuffer;
import jena.lang.StructPair;
import jena.lang.text.StringText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public final class JavaObjectValue implements Value
{
    Object object;
    Namespace members;

    public JavaObjectValue(Object object)
    {
        this.object = object;
        members = new HashMapNamespace(
            new ArrayBuffer<>(
                object.getClass().getDeclaredMethods()).map(m -> new StructPair<Text, Value>(
                    new StringText(m.getName()),
                    new JavaMethodValue(m))));
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write(new StringText(object.toString()));
    }

    @Override
    public Value call(Value argument)
    {
        return NoneValue.instance;
    }

    @Override
    public boolean valueEquals(Value v)
    {
        return v instanceof JavaObjectValue j && j.object.equals(object);
    }
}