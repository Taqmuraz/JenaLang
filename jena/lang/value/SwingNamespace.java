package jena.lang.value;

import jena.lang.ArrayBuffer;
import jena.lang.text.StringText;
import jena.lang.text.Text;

public final class SwingNamespace implements Namespace
{
    private Namespace names;

    public SwingNamespace()
    {
        names = new SingleNamespace(new StringText("Window"),
            new MethodValue(
                new ArrayBuffer<Text>(
                    new StringText("width"),
                    new StringText("height")
                ),
                args ->
                {
                    return new SwingWindowValue(
                        new ExpressionIntegerNumber(args.at(0)),
                        new ExpressionIntegerNumber(args.at(1)));
                }
            ));
    }

    @Override
    public Value name(Text name)
    {
        return names.name(name);
    }
}