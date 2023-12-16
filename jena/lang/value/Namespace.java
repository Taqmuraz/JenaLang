package jena.lang.value;

import jena.lang.text.Text;

public interface Namespace
{
    ValueFunction name(Text name);

    default Namespace nested(Namespace inner)
    {
        return new NestedNamespace(this, inner);
    }

    static final Namespace functions = new HashMapNamespace(action ->
    {
        action.call("box", () -> new FunctionValue("boxArg", BoxValue::new));
        action.call("int", () -> new FunctionValue("toInt", arg -> new NumberValue(Single.of(arg).integer())));
    });

    static final Namespace standard = new StorageNamespace(
        new IONamespace().nested(
            new SwingNamespace().nested(
                JavaNamespace.create()).nested(functions)));
}