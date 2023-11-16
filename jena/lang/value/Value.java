package jena.lang.value;

import java.util.List;

import jena.lang.GenericBuffer;
import jena.lang.source.Source;
import jena.lang.syntax.Syntax;
import jena.lang.text.TextWriter;
import jena.lang.text.ValueText;

public interface Value
{
    void print(TextWriter writer);
    Value call(Value argument);
    boolean valueEquals(Value v);
    int valueCode();
    Object toObject(Class<?> type);

    default Value managedCall(Object... args)
    {
        return call(new ArrayValue(GenericBuffer.of(List.of(args)).flow().map(JavaObjectValue::fromObject).collect()));
    }

    default String string()
    {
        return new ValueText(this).string();
    }

    static Value of(Source source, Namespace namespace)
    {
        return Syntax.read(source).itemOrThrow(mistake ->
        {
            StringBuilder sb = new StringBuilder();
            mistake.print(sb::append);
            return new RuntimeException(sb.toString());
        }).value(namespace).call();
    }
    static Value of(Source source)
    {
        return of(source, Namespace.standard);
    }
}