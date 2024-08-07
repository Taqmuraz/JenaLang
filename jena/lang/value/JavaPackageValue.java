package jena.lang.value;

import jena.lang.text.TextWriter;

public class JavaPackageValue implements Value
{
    private final String name;

    public JavaPackageValue(String name)
    {
        this.name = name;
    }
    public JavaPackageValue(Value name)
    {
        this.name = stringFromValue(name);
    }

    static String stringFromValue(Value value)
    {
        if(value instanceof SymbolValue s)
        {
            return s.name;
        }
        throw new RuntimeException(String.format("Argument %s was not expected, it must be only symbol", value.string()));
    }

    @Override
    public void print(TextWriter writer)
    {
        writer.write(String.format("(javaPackage %s)", name));
    }

    @Override
    public Value call(Value argument)
    {
        String n = stringFromValue(argument);
        if(n.length() == 0) throw new RuntimeException("Class or package name must have more than zero characters");
        String path = String.format("%s.%s", name, n);
        if(Character.isUpperCase(n.charAt(0)))
        {
            return new JavaClassValue(path);
        }
        return new JavaPackageValue(path);
    }

    @Override
    public boolean valueEquals(Value v)
    {
        if(v instanceof JavaPackageValue j) return name.equals(j.name);
        return false;
    }

    @Override
    public int valueCode()
    {
        return hashCode();
    }

    @Override
    public Object toObject(Class<?> type)
    {
        return this;
    }   
}