package jena.lang;

import jena.lang.source.Source;
import jena.lang.source.StringSource;
import jena.lang.value.AnonymousMethodValue;
import jena.lang.value.Namespace;
import jena.lang.value.ObjectValue;
import jena.lang.value.Value;

public final class IONamespace implements Namespace
{
    private ObjectValue members;

    public IONamespace()
    {
        members = new ObjectValue(new ArrayGenericFlow<String>(new String[]
        {
            "print"
        }).<Source>map(StringSource::new).zip(new SingleGenericFlow<Value>(
            new AnonymousMethodValue(1, args ->
            {
                Value arg = args.at(0);
                arg.print(s -> System.out.print(s.text().toString()));
                return arg;
            })
        )).collect());
    }

    @Override
    public Value name(Source name)
    {
        return members.member(name);
    }
}