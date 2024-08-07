package jena.lang.value;

public final class JavaNamespace
{
    public static Namespace create()
    {
        return new HashMapNamespace(action ->
        {
            action.call("java", () -> new JavaPackageValue("java"));
            action.call("import", () -> new FunctionValue("symbol", arg -> new JavaPackageValue(arg)));
            action.call("javaClass", () -> new FunctionValue("classPath", arg -> new JavaClassValue(arg.string())));
            action.call("javaArray", () -> new FunctionValue("classPath", arg -> new JavaClassValue(String.format("[L%s;", arg.string()))));
        });
    }
}