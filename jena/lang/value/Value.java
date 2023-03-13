package jena.lang.value;

import jena.lang.text.Text;
import jena.lang.text.TextWriter;

public interface Value
{
    void print(TextWriter writer);
    Value member(Text name);
    Value call(ArgumentList arguments);
}