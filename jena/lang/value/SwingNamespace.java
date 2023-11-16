package jena.lang.value;

import jena.lang.text.StringText;
import jena.lang.text.Text;

public final class SwingNamespace implements Namespace
{
    private Namespace names;

    public SwingNamespace()
    {
        names = new SingleNamespace(new StringText("Window"), ValueFunction.of(new FunctionValue("width", width ->
            new FunctionValue("height", height ->
        {
            return new SwingWindowValue(
                new ExpressionNumber(width),
                new ExpressionNumber(height));
        }))));
    }

    @Override
    public ValueFunction name(Text name)
    {
        return names.name(name);
    }
}