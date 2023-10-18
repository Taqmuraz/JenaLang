package jena.lang.value;

import jena.lang.ActionTwo;

public interface SymbolValuePair
{
    void use(ActionTwo<String, ValueFunction> action);
}