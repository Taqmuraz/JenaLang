package jena.lang.value;

import jena.lang.text.StringText;
import jena.lang.text.Text;

public final class SwingNamespace implements Namespace
{
    private Namespace names;

    public SwingNamespace()
    {
        names = new SingleNamespace(new StringText("Window"), new MethodValue("width", width ->
            new MethodValue("height", height ->
        {
            return new SwingWindowValue(
                new ExpressionNumber(width),
                new ExpressionNumber(height));
        })));
    }

    @Override
    public Value name(Text name)
    {
        return names.name(name);
    }
}