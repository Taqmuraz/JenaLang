package jena.lang.value;

import jena.lang.text.StringText;
import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public final class TextValue implements Value
{
    public final Text text;
    private ValueFunction elements;
    private static MembersMap<TextValue> members;

    interface BinaryTextFunction
    {
        Value call(Text a, Text b);
    }

    static MembersMap.Member<TextValue> textFunction(String argumentName, BinaryTextFunction func)
    {
        return self -> new FunctionValue(argumentName, arg -> func.call(self.text, Text.of(arg)));
    }

    public TextValue(Text text)
    {
        this.text = text;
        elements = () -> new ArrayValue(text.buffer().<Value>map(CharacterValue::new));

        members = new MembersMap<>(action ->
        {
            action.call("add", textFunction("addition", (a, b) -> new TextValue(a.concat(b))));
            action.call("equals", textFunction("equalsTo", (a, b) -> new NumberValue(a.compare(b))));
            action.call("notEquals", textFunction("notEqualsTo", (a, b) -> new NumberValue(!a.compare(b))));
            action.call("buff", self -> new TextValue(Text.of(self.text.string())));
            action.call("size", self -> new NumberValue(text.length()));
            action.call("elements", self -> elements.call());
            action.call("float", self -> Single.of(self.text));
        });
    }
    public TextValue(String text)
    {
        this(new StringText(text));
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write(text);
    }

    @Override
    public Value call(Value argument)
    {
        if(argument instanceof SymbolValue symbol)
        {
            return members.member(symbol.name.string(), self -> NoneValue.instance).call(this);
        }
        return elements.call().call(argument);
    }
    @Override
    public boolean valueEquals(Value v)
    {
        return v instanceof TextValue t && t.text.compare(text);
    }
    @Override
    public Object toObject(Class<?> type)
    {
        if(type.isAssignableFrom(String.class)) return text.string();
        else throw new RuntimeException(String.format("%s is not assignable from the String type", type.getName()));
    }
    @Override
    public int valueCode()
    {
        return text.string().hashCode();
    }
}