package jena.syntax;

import jena.lang.source.Source;

public final class EmptyNamespace implements Namespace
{
    public final static Namespace instance = new EmptyNamespace();

    @Override
    public Value name(Source name)
    {
        return NoneValue.instance;
    }
}