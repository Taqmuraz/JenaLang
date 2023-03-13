package jena.lang.text;

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
}