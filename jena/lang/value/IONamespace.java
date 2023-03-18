package jena.lang.value;

import jena.lang.EmptyBuffer;
import jena.lang.EmptyGenericFlow;
import jena.lang.GenericPair;
import jena.lang.SingleBuffer;
import jena.lang.StructPair;
import jena.lang.source.InputStreamLineSource;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.StringText;
import jena.lang.text.Text;

public final class IONamespace implements Namespace
{
    private Namespace names;

    public IONamespace()
    {
        names = new HashMapNamespace(new EmptyGenericFlow<GenericPair<Text, Value>>()
        .append(new StructPair<>(new StringText("print"), new MethodValue(new SingleBuffer<Text>(new StringText("text")), args ->
        {
            Value arg = args.at(0);
            arg.print(s -> System.out.print(s.string()));
            return arg;
        })))
        .append(new StructPair<Text, Value>(new StringText("read"), new MethodValue(new EmptyBuffer<>(), args -> new TextValue(new InputStreamLineSource(System.in).text()))))
        .append(new StructPair<Text, Value>(new StringText("readInt"), new MethodValue(new EmptyBuffer<>(), args ->
        {
            try
            {
                int value = Integer.valueOf(new InputStreamLineSource(System.in).text().string());
                return new IntegerValue(value);
            }
            catch(Throwable error)
            {
                return NoneValue.instance;
            }
        })))
        .append(new StructPair<Text, Value>(new StringText("line"), new TextValue(new SingleCharacterText('\n'))))
        .append(new StructPair<Text, Value>(new StringText("space"), new TextValue(new SingleCharacterText(' '))))
        .collect());
    }

    @Override
    public Value name(Text name)
    {
        return names.name(name);
    }
}