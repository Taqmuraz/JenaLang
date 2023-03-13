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
    private ObjectValue members;

    public IONamespace()
    {
        members = new ObjectValue(new EmptyGenericFlow<GenericPair<Text, Value>>()
        .append(new StructPair<>(new StringText("print"), new AnonymousMethodValue(new SingleBuffer<Text>(new StringText("text")), args ->
        {
            Value arg = args.at(0);
            arg.print(s -> System.out.print(s.string()));
            return arg;
        })))
        .append(new StructPair<Text, Value>(new StringText("read"), new AnonymousMethodValue(new EmptyBuffer<>(), args -> new TextValue(new InputStreamLineSource(System.in).text()))))
        .append(new StructPair<Text, Value>(new StringText("readInt"), new AnonymousMethodValue(new EmptyBuffer<>(), args -> new IntegerValue(Integer.valueOf(new InputStreamLineSource(System.in).text().string())))))
        .append(new StructPair<Text, Value>(new StringText("line"), new TextValue(new SingleCharacterText('\n'))))
        .append(new StructPair<Text, Value>(new StringText("space"), new TextValue(new SingleCharacterText(' '))))
        .collect());
    }

    @Override
    public Value name(Text name)
    {
        return members.member(name);
    }
}