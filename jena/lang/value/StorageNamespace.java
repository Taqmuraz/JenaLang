package jena.lang.value;

import java.io.File;

import jena.lang.ArrayGenericFlow;
import jena.lang.source.FileSource;
import jena.lang.source.Source;
import jena.lang.source.StringSource;
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

    static Syntax loadFromSource(Source source)
    {
        Syntax[] value = { null };
        new JenaSyntaxReader(source).read(syntax ->
        {
            value[0] = syntax;
        }, new TextSyntaxMistakePrinter());
        if(value[0] == null) throw new RuntimeException("Error while reading source : " + source.text().string());
        return value[0];
    }

    static Syntax loadFromFile(String name)
    {
        File file = new File(name);

        if(file.exists())
        {
            return loadFromSource(new FileSource(file));
        }
        else throw new RuntimeException("Error while opening file : " + name);
    }

    public StorageNamespace(Namespace namespace)
    {
        this.namespace = namespace.nested(
            new PairNamespace(
                new ArrayGenericFlow<Text>(
                    new StringText("source"),
                    new StringText("inspect"),
                    new StringText("inspectl")).<Value>zip(
                        action ->
                        {
                            action.call(new FunctionValue("fileName", arg ->
                            {
                                return loadFromFile(new ValueText(arg).string()).value(this);
                            }));
                            action.call(new FunctionValue("fileName", arg ->
                            {
                                return new TextValue(new SyntaxText(loadFromFile(new ValueText(arg).string())));
                            }));
                            action.call(new FunctionValue("line", arg ->
                            {
                                return new TextValue(new SyntaxText(loadFromSource(
                                    new StringSource(new StringText("line"), new ValueText(arg).string()))));
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