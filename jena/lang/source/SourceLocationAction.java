package jena.lang.source;

import jena.lang.text.Text;

public interface SourceLocationAction
{
    void call(Text origin, int line, int symbol);
}