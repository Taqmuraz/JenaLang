package jena.lang.value;

import jena.lang.ArrayGenericFlow;
import jena.lang.SingleGenericFlow;
import jena.lang.source.InputStreamLineSource;
import jena.lang.source.SingleCharacterSource;
import jena.lang.source.Source;
import jena.lang.source.StringSource;

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
        }).<Source>map(StringSource::new).zip(new SingleGenericFlow<Value>(
            new AnonymousMethodValue(1, args ->
            {
                Value arg = args.at(0);
                arg.print(s -> System.out.print(s.text().toString()));
                return arg;
            })
        )
        .append(new AnonymousMethodValue(0, args -> new TextValue(new InputStreamLineSource(System.in))))
        .append(new AnonymousMethodValue(0, args -> new IntegerValue(Integer.valueOf(new InputStreamLineSource(System.in).text().toString()))))
        .append(new TextValue(new SingleCharacterSource('\n')))
        .append(new TextValue(new SingleCharacterSource(' ')))).collect());
    }

    @Override
    public Value name(Source name)
    {
        return members.member(name);
    }
}