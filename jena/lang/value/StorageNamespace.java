package jena.lang.value;

import java.io.File;

import jena.lang.ArrayGenericFlow;
import jena.lang.source.FileSource;
import jena.lang.syntax.JenaSyntaxReader;
import jena.lang.syntax.Syntax;
import jena.lang.syntax.TextSyntaxMistakePrinter;
import jena.lang.text.StringText;
import jena.lang.text.SyntaxText;
import jena.lang.text.Text;
import jena.lang.text.ValueText;

public final class StorageNamespace implements Namespace
{
    private Namespace namespace;

    static Syntax loadFromFile(String name)
    {
        Syntax[] value = { null };
        File file = new File(name);

        if(file.exists())
        {
            new JenaSyntaxReader(new FileSource(file)).read(syntax ->
            {
                value[0] = syntax;
            }, new TextSyntaxMistakePrinter());
        }
        if(value[0] == null) throw new RuntimeException("Error while opening file : " + name); 
        return value[0];
    }

    public StorageNamespace(File root, Namespace namespace)
    {
        this.namespace = namespace.nested(
            new PairNamespace(
                new ArrayGenericFlow<Text>(
                    new StringText("source"),
                    new StringText("inspect")).<Value>zip(
                        action ->
                        {
                            action.call(new MethodValue("fileName", arg ->
                            {
                                return loadFromFile(new ValueText(arg).string()).value(this);
                            }));
                            action.call(new MethodValue("fileName", arg ->
                            {
                                return new TextValue(new SyntaxText(loadFromFile(new ValueText(arg).string())));
                            }));
                        })
                        .collect()));
    }

    @Override
    public Value name(Text name)
    {
        return namespace.name(name);
    }
}