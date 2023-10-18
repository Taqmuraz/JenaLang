package jena.lang.value;

import java.io.File;

import jena.lang.SingleGenericFlow;
import jena.lang.source.FileSource;
import jena.lang.syntax.JenaSyntaxReader;
import jena.lang.syntax.TextSyntaxMistakePrinter;
import jena.lang.text.StringText;
import jena.lang.text.Text;
import jena.lang.text.ValueText;

public final class StorageNamespace implements Namespace
{
    private Namespace namespace;

    public StorageNamespace(File root, Namespace namespace)
    {
        this.namespace = namespace.nested(
            new PairNamespace(
                new SingleGenericFlow<Text>(
                    new StringText("source")).zip(
                        new SingleGenericFlow<Value>(new MethodValue(new TupleValue(new TextValue("fileName")), arg ->
                        {
                            Text name = new ValueText(arg);
                            Value[] value = { NoneValue.instance };
                            File file = new File(name.string());

                            if(file.exists())
                            {
                                new JenaSyntaxReader(new FileSource(file)).read(syntax ->
                                {
                                    value[0] = syntax.value(this);
                                }, new TextSyntaxMistakePrinter());
                            }

                            return value[0];
                        })))
                        .collect()));
    }

    @Override
    public Value name(Text name)
    {
        return namespace.name(name);
    }
}