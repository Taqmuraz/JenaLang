package jena.lang.value;

import jena.lang.ArrayGenericFlow;
import jena.lang.SingleGenericFlow;
import jena.lang.source.InputStreamLineSource;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.StringText;
import jena.lang.text.Text;

public final class IONamespace implements Namespace
{
    private ObjectValue members;

    public IONamespace()
    {
        members = new ObjectValue(new ArrayGenericFlow<String>(new String[]
        {
            "print",
            "read",
            "readInt",
            "line",
            "space",
        }).<Text>map(StringText::new).zip(new SingleGenericFlow<Value>(
            new AnonymousMethodValue(1, args ->
            {
                Value arg = args.at(0);
                arg.print(s -> System.out.print(s.string()));
                return arg;
            })
        )
        .append(new AnonymousMethodValue(0, args -> new TextValue(new InputStreamLineSource(System.in).text())))
        .append(new AnonymousMethodValue(0, args -> new IntegerValue(Integer.valueOf(new InputStreamLineSource(System.in).text().string()))))
        .append(new TextValue(new SingleCharacterText('\n')))
        .append(new TextValue(new SingleCharacterText(' ')))).collect());
    }

    @Override
    public Value name(Text name)
    {
        return members.member(name);
    }
}