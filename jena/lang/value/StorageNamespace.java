package jena.lang.value;

import java.io.File;

import jena.lang.SingleGenericFlow;
import jena.lang.source.FileSource;
import jena.lang.source.Source;
import jena.lang.source.StringSource;
import jena.lang.syntax.JenaSyntaxReader;

public final class StorageNamespace implements Namespace
{
    private Namespace namespace;

    public StorageNamespace(File root, Namespace namespace)
    {
        this.namespace = namespace.nested(
            new PairNamespace(
                new SingleGenericFlow<Source>(
                    new StringSource("source")).zip(
                        new SingleGenericFlow<Value>(new AnonymousMethodValue(1, args ->
                        {
                            Source[] name = new Source[1];
                            args.at(0).print(s -> name[0] = s);
                            if(name[0] == null) return NoneValue.instance;
                            Value[] value = { NoneValue.instance };
                            File file = new File(name[0].text().toString());

                            if(file.exists())
                            {
                                new JenaSyntaxReader(new FileSource(file)).read(syntax ->
                                {
                                    value[0] = syntax.value(this);
                                });
                            }

                            return value[0];
                        })))
                        .collect()));
    }

    @Override
    public Value name(Source name)
    {
        return namespace.name(name);
    }
}