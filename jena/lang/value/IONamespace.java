package jena.lang.value;

import jena.lang.source.InputStreamLineSource;
import jena.lang.text.SingleCharacterText;
import jena.lang.text.Text;

public final class IONamespace implements Namespace
{
    private Namespace names;

    public IONamespace()
    {
        names = new HashMapNamespace(action ->
        {
            action.call("print", () -> new FunctionValue("text", (arg, self) ->
            {
                arg.print(s -> System.out.print(s.string()));
                return self;
            }));
            action.call("read", () -> new FunctionValue(args -> new TextValue(new InputStreamLineSource(System.in).text())));
            action.call("readInt", () -> new FunctionValue(args ->
            {
                try
                {
                    int value = Integer.valueOf(new InputStreamLineSource(System.in).text().string());
                    return new NumberValue(value);
                }
                catch(Throwable error)
                {
                    return NoneValue.instance;
                }
            }));
            action.call("line", () -> new TextValue(new SingleCharacterText('\n')));
            action.call("space", () -> new TextValue(new SingleCharacterText(' ')));
        });
    }

    @Override
    public ValueFunction name(Text name)
    {
        return names.name(name);
    }
}