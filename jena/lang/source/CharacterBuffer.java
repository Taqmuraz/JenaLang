package jena.lang.source;

public interface CharacterBuffer
{
    char at(int index);
    int length();
    
    default boolean compare(CharacterBuffer other)
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
        return compare(new StringCharacterBuffer(string));
    }
}