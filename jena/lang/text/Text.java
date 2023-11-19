package jena.lang.text;

import jena.lang.GenericBuffer;
import jena.lang.syntax.Syntax;
import jena.lang.value.Value;

public interface Text
{
    char at(int index);
    int length();
    @Deprecated
    String toString();

    default String string()
    {
        int length = length();
        char[] buffer = new char[length];
        for(int i = 0; i < length; i++) buffer[i] = at(i);
        return String.copyValueOf(buffer);
    }
    
    default boolean compare(Text other)
    {
        int length = length();
        if(length != other.length()) return false;

        for(int i = 0; i < length; i++)
        {
            if(at(i) != other.at(i)) return false;
        }
        return true;
    }
    default boolean compareString(String string)
    {
        return compare(new StringText(string));
    }
    default GenericBuffer<Character> buffer()
    {
        return new GenericBuffer<Character>()
        {
            @Override
            public int length()
            {
                return Text.this.length();
            }

            @Override
            public Character at(int index)
            {
                return Text.this.at(index);
            }
        };
    }

    default Text concat(Text text)
    {
        return new ConcatText(this, text);
    }

    static Text of(char c)
    {
        return new SingleCharacterText(c);
    }
    static Text of(String s)
    {
        return new StringText(s);
    }
    static Text of(Syntax s)
    {
        return new SyntaxText(s);
    }
    static Text of(Value v)
    {
        return new ValueText(v);
    }
}