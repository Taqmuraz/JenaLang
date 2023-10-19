package jena.lang.value;

import jena.lang.text.StringText;
import jena.lang.text.Text;

public final class SwingNamespace implements Namespace
{
    private Namespace names;

    public SwingNamespace()
    {
        names = new SingleNamespace(new StringText("Window"), new MethodValue(new TupleValue(
            new TextValue("width"),
            new TextValue("height")),
        arg ->
        {
            var args = arg.decompose();
            return new SwingWindowValue(
                new ExpressionNumber(args.at(0)),
                new ExpressionNumber(args.at(1)));
        }));
    }

    @Override
    public Value name(Text name)
    {
        return names.name(name);
    }
}